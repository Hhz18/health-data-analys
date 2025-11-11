package com.healthdata.controller;

import com.healthdata.service.HiveService;
import com.healthdata.service.OcrService;
import com.healthdata.utils.HdfsUtil;
import com.tencentcloudapi.ocr.v20181119.models.RecognizeTableAccurateOCRResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Base64;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    @Autowired
    private HdfsUtil hdfsUtil;

    @Autowired
    private OcrService ocrService;

    @Autowired
    private HiveService hiveService;

    @Autowired
    private com.healthdata.mappers.UploadedFileMapper uploadedFileMapper;

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam("category") String category,
                                        @RequestParam("tag") String tag,
                                        @RequestParam("year") String year) {
        try {
            // 1. 上传文件到 HDFS，返回 HDFS 路径
            String hdfsPath = hdfsUtil.uploadFile(file, category);
            logger.info("File uploaded to HDFS: {} (category: {}, tag: {}, year: {})", hdfsPath, category, tag, year);

            // 2. 获取文件名和类型
            String fileName = file.getOriginalFilename();
            String fileType = fileName != null && fileName.contains(".") ? fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase() : "";
            String ocrJson = null;

            // 3. 判断是否为图片类型，若是则调用 OCR 服务
            if (fileType.matches("jpg|jpeg|png|bmp|gif|tif|tiff|webp")) {
                // 3.1 读取图片内容并转为Base64字符串
                String imageBase64;
                try (InputStream in = file.getInputStream()) {
                    byte[] imageBytes = in.readAllBytes();
                    imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
                }
                // 3.2 调用 OCR 服务，传递 Base64 编码图片
                String ocrResp = ocrService.ocrByBase64(imageBase64);
                if (ocrResp != null) {
                    // 用 Jackson 将 ocrResp 转为标准 JSON 字符串
//                    com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
//                    ocrJson = mapper.writeValueAsString(ocrResp);
                    logger.info("OCR result for {}: {}", fileName, ocrResp);
                    //调用 HiveService，将 OCR 结果导入 Hive，按分类自动建表
                    hiveService.importOcrJson(year, ocrResp);
                } else {
                    logger.warn("OCR failed or returned null for file: {}", fileName);
                }
            }

            // 4. 保存文件信息到数据库
            com.healthdata.entity.UploadedFile uploadedFile = new com.healthdata.entity.UploadedFile();
            uploadedFile.setName(fileName);
            uploadedFile.setCat(category);
            uploadedFile.setTag(tag);
            uploadedFile.setYear(year);
            uploadedFileMapper.insertUploadedFile(uploadedFile);

            // 5. 返回上传结果，包含 OCR 结果（如有）
            return ResponseEntity.ok("File uploaded to HDFS: " + hdfsPath + ", tag: " + tag + ", year: " + year + (ocrJson != null ? ", ocr: " + ocrJson : ""));
        } catch (Exception e) {
            // 6. 异常处理，记录日志并返回错误信息
            logger.error("Upload failed", e);
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }
}
