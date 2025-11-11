package com.healthdata.service;

import com.healthdata.entity.Cat;
import java.util.List;

public interface CatService {
    void addCat(String name);
    void deleteCat(Long id);
    List<Cat> getAllCats();
}
