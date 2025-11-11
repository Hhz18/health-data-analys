package com.healthdata.service;

import com.healthdata.entity.Cat;
import com.healthdata.mappers.CatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CatServiceImpl implements CatService {
    @Autowired
    private CatMapper catMapper;

    @Override
    public void addCat(String name) {
        Cat cat = new Cat();
        cat.setName(name);
        catMapper.insertCat(cat);
    }

    @Override
    public void deleteCat(Long id) {
        catMapper.deleteCat(id);
    }

    @Override
    public List<Cat> getAllCats() {
        return catMapper.selectAllCats();
    }
}
