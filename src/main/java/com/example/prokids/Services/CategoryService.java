package com.example.prokids.Services;

import com.example.prokids.Model.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    Category findById(String id);
    Category saveCategory(Category category);
    void deleteCategoryById(String id);
    List<Category> getAllCategory();
    Category addImages(String id, MultipartFile file);
}
