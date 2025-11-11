package com.healthdata.mappers;

import com.healthdata.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface TagMapper {
    int insertTag(Tag tag);
    int deleteTag(Long id);
    List<Tag> selectAllTags();
}
