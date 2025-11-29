package com.example.labadva.controller;

import com.example.labadva.model.Category;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final List<Category> categories = new ArrayList<>();

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        categories.add(category);
        return category;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categories;
    }
}
