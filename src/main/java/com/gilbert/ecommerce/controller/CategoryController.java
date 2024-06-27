package com.gilbert.ecommerce.controller;

import com.gilbert.ecommerce.model.Category;
import com.gilbert.ecommerce.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("api/public/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> allCategories = categoryService.getAllCategories();
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @PostMapping("api/public/categories")
    public ResponseEntity<String> createCategory(@RequestBody Category category) {
        if (category.getCategoryName() == null || category.getCategoryName().isEmpty()) {
            return new ResponseEntity<>("Category name cannot be empty", HttpStatus.BAD_REQUEST);
        }

        try {
            categoryService.createCategory(category);
            return new ResponseEntity<>("Category created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while creating the category", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("api/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        try {
            String status = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @PutMapping("api/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@RequestBody Category category, @PathVariable Long categoryId) {
        if (category.getCategoryName() == null || category.getCategoryName().isEmpty()) {
            return new ResponseEntity<>("Category name cannot be empty", HttpStatus.BAD_REQUEST);
        }

        try {
            Category updatedCategory = categoryService.updateCategory(category, categoryId);
            return new ResponseEntity<>("Category updated successfully", HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }
}