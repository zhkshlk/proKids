package com.example.prokids.Services;

import com.example.prokids.Model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category findById(String id);
    Category saveCategory(Category category);
    void deleteCategoryById(String id);
    List<Category> getAllCategory();
}
