package com.example.prokids.Services.Impl;

import com.example.prokids.Model.Category;
import com.example.prokids.Services.CategoryService;
import com.example.prokids.repositories.CategoryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private static final String UPLOAD_DIR = "uploads/images/category";

    private final CategoryRepository categoryRepository;

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

    @Override
    public Category addImages(String id, MultipartFile file) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            try {
                    Category category = optionalCategory.get();
                    String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                    Path path = Paths.get(UPLOAD_DIR, filename);
                    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    category.setImage("/uploads/images/category/" + filename);

                    categoryRepository.save(category);
                    return category;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
