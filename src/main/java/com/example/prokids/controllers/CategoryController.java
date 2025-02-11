package com.example.prokids.controllers;

import com.example.prokids.Model.Category;
import com.example.prokids.Services.CategoryService;
import com.example.prokids.dto.CategoryCreate;
import com.example.prokids.dto.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@Tag(name = "Категории")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Категория")
    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<CategoryResponse> getProduct (@PathVariable("id") String id) {
        Category category = categoryService.findById(id);
        CategoryResponse categoryResponse = new CategoryResponse(category);

        return ResponseEntity.ok(categoryResponse);
    }
    @Operation(summary = "Категории")
    @GetMapping
    @PreAuthorize("permitAll()")
    private ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategory();
        List<CategoryResponse> categoryResponses = categories.stream().map(CategoryResponse::new).toList();
        return ResponseEntity.ok(categoryResponses);
    }

    @Operation(summary = "Создать")
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoryResponse> createCategory(
            @RequestBody CategoryCreate request
    ) {
        Category category = new Category(request);
        Category savedCategory = categoryService.saveCategory(category);
        CategoryResponse categoryResponse = new CategoryResponse(savedCategory);
        return ResponseEntity.ok(categoryResponse);
    }

    @Operation(summary = "Добавить изображение категории")
    @PostMapping("/addImage/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addImage(
            @PathVariable String id,
            MultipartFile file
    ) {
        Category category = categoryService.addImages(id, file);

        CategoryResponse categoryResponse = new CategoryResponse(category);
        return ResponseEntity.ok(categoryResponse);

    }

    @Operation(summary = "Удалить")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        categoryService.deleteCategoryById(id);
        ResponseEntity.ok().build();
        return null;
    }

}
