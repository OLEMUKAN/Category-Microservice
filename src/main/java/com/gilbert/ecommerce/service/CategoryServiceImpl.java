package com.gilbert.ecommerce.service;

import com.gilbert.ecommerce.model.Category;
import com.gilbert.ecommerce.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{


    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            categoryRepository.delete(category.get());
            return "Category deleted successfully";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Optional<Category> categoryToUpdate = categoryRepository.findById(categoryId);
        if (categoryToUpdate.isPresent()) {
            Category existingCategory = categoryToUpdate.get();
            existingCategory.setCategoryName(category.getCategoryName());
            categoryRepository.save(existingCategory);
            return existingCategory;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
    }
}