package com.healthdata.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class HdfsUtil {
    @Value("${hdfs.uri}")
    private String hdfsUri;

    @Value("${hdfs.user}")
    private String hdfsUser;

    @Value("${hdfs.base-path}")
    private String basePath;

    private FileSystem fileSystem;

    private static final Logger logger = LoggerFactory.getLogger(HdfsUtil.class);

    @PostConstruct
    public void init() throws IOException, InterruptedException, URISyntaxException {
        // 使用 classpath 下的 core-site.xml 和 hdfs-site.xml 自动加载配置，并指定用户为 hadoop
        Configuration conf = new Configuration();
        fileSystem = FileSystem.get(new URI(conf.get("fs.defaultFS")), conf, "hadoop");
        logger.info("HDFS FileSystem initialized from classpath configuration as user 'hadoop'.");
    }

    public String uploadFile(MultipartFile file, String category) throws IOException {
        // 强制以 hadoop 用户身份执行
        org.apache.hadoop.security.UserGroupInformation.setLoginUser(
            org.apache.hadoop.security.UserGroupInformation.createRemoteUser("hadoop")
        );
        String fileName = file.getOriginalFilename();
        String destPath = "/health/rawdata/" + category + "/" + fileName;
        Path path = new Path(destPath);
        logger.debug("Uploading file: {} to HDFS path: {}", fileName, destPath);
        try (InputStream in = file.getInputStream();
             org.apache.hadoop.fs.FSDataOutputStream out = fileSystem.create(path, true)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            logger.info("File [{}] uploaded to HDFS path [{}] successfully.", fileName, destPath);
        } catch (IOException e) {
            logger.error("Failed to upload file [{}] to HDFS path [{}]", fileName, destPath, e);
            throw e;
        }
        return destPath;
    }
}
