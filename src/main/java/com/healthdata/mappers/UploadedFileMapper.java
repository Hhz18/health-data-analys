package com.healthdata.mappers;

import com.healthdata.entity.UploadedFile;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UploadedFileMapper {
    int insertUploadedFile(UploadedFile file);
    // 可扩展：查询、删除等
}

