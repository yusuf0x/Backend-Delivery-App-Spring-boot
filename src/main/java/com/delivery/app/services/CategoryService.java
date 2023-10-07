package com.delivery.app.services;

import com.delivery.app.exceptions.CategoryNotFoundException;
import com.delivery.app.models.Category;
import com.delivery.app.repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepo categoryRepo;
    @Autowired
    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }
    public void addCategory(Category category) {
        categoryRepo.save(category);
    }
    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category Not Found with id "+id)
        );
    }
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

}
