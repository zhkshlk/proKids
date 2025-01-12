package com.example.prokids.Services.Impl;

import com.example.prokids.Model.Category;
import com.example.prokids.Services.CategoryService;
import com.example.prokids.repositories.CategoryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor

public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    @Override
    public Category findById(String id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategoryById(String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}
