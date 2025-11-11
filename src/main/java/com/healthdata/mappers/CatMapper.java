package com.healthdata.mappers;

import com.healthdata.entity.Cat;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CatMapper {
    int insertCat(Cat cat);
    int deleteCat(Long id);
    List<Cat> selectAllCats();
}
