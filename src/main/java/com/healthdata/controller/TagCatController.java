package com.healthdata.controller;

import com.healthdata.entity.Tag;
import com.healthdata.entity.Cat;
import com.healthdata.service.TagService;
import com.healthdata.service.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TagCatController {
    @Autowired
    private TagService tagService;
    @Autowired
    private CatService catService;

    // Tag APIs
    @PostMapping("/tag")
    public void addTag(@RequestParam String name) {
        tagService.addTag(name);
    }

    @DeleteMapping("/tag/{id}")
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
    }

    @GetMapping("/tag")
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    // Cat APIs
    @PostMapping("/cat")
    public void addCat(@RequestParam String name) {
        catService.addCat(name);
    }

    @DeleteMapping("/cat/{id}")
    public void deleteCat(@PathVariable Long id) {
        catService.deleteCat(id);
    }

    @GetMapping("/cat")
    public List<Cat> getAllCats() {
        return catService.getAllCats();
    }
}

