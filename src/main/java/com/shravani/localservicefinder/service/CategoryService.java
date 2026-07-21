package com.shravani.localservicefinder.service;

import com.shravani.localservicefinder.dto.CategoryRequest;
import com.shravani.localservicefinder.entity.Category;
import com.shravani.localservicefinder.exception.ResourceNotFoundException;
import com.shravani.localservicefinder.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(CategoryRequest request) {
        String categoryName = request.getName().trim();

        if (categoryRepository.existsByNameIgnoreCase(categoryName)) {
            throw new RuntimeException("Category already exists with name: " + categoryName);
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setDescription(request.getDescription());
        category.setActive(true);

        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> getActiveCategories() {
        return categoryRepository.findByActiveTrue();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    public Category updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        String categoryName = request.getName().trim();

        if (categoryRepository.existsByNameIgnoreCaseAndIdNot(categoryName, id)) {
            throw new RuntimeException("Category already exists with name: " + categoryName);
        }

        category.setName(categoryName);
        category.setDescription(request.getDescription());

        return categoryRepository.save(category);
    }

    public Category updateCategoryStatus(Long id, Boolean active) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        category.setActive(active);

        return categoryRepository.save(category);
    }
}