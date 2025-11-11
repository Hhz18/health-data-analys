package com.healthdata.service;

import com.healthdata.entity.Tag;
import java.util.List;

public interface TagService {
    void addTag(String name);
    void deleteTag(Long id);
    List<Tag> getAllTags();
}
