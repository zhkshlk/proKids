package com.example.prokids.controllers;

import com.example.prokids.Model.Category;
import com.example.prokids.Services.CategoryService;
import com.example.prokids.Services.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

public class CategoryController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}/image")
    public ResponseEntity<String> getCategoryImage(
            @Parameter(description = "ID категории", required = true) @PathVariable String id
    ) {
        Category category = categoryService.findById(id);
        if (category != null && category.getImage() != null) {
            String base64Image = category.getImage();
            return new ResponseEntity<>(base64Image, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("new_category", new Category());
        model.addAttribute("all_categories", categoryService.getAllCategory());
        return "create-category";
    }

    @PostMapping("/create")
    public String createCategory(
            @Parameter(description = "Данные категории", required = true) @ModelAttribute Category category,
            @Parameter(description = "Изображение категории", required = true) @RequestParam("imageFile") MultipartFile imageFile
    ) throws IOException {
        if (!imageFile.isEmpty()) {
            byte[] imageBytes = imageFile.getBytes();
            String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
            category.setImage(encodedImage);
        }
        categoryService.saveCategory(category);
        return "redirect:/categories/create";
    }
}
