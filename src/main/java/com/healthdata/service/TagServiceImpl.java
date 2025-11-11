package com.healthdata.service;

import com.healthdata.entity.Tag;
import com.healthdata.mappers.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public void addTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        tagMapper.insertTag(tag);
    }

    @Override
    public void deleteTag(Long id) {
        tagMapper.deleteTag(id);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagMapper.selectAllTags();
    }
}
