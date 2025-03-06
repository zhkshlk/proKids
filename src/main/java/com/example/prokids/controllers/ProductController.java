package com.example.prokids.controllers;

import com.example.prokids.Model.Category;
import com.example.prokids.Model.Product;
import com.example.prokids.Services.ProductService;
import com.example.prokids.dto.ProductCreate;
import com.example.prokids.dto.ProductResponse;
import com.example.prokids.repositories.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Товары")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    @Operation(summary = "Продукты")
    @GetMapping()
    @PreAuthorize("permitAll()")
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String category,
            Pageable pageable
    ) {
        Page<Product> products = productService.getAllProduct(minPrice, maxPrice, category, pageable);

        Page<ProductResponse> productResponses = products.map(ProductResponse::new);

        return ResponseEntity.ok(productResponses);
    }

    @Operation(summary = "Продукт")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct (@PathVariable("id") String id) {
        Product product = productService.findById(id);
        ProductResponse productResponse = new ProductResponse(product);

        return ResponseEntity.ok(productResponse);
    }

    @Operation(summary = "Создать")
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductResponse> create(@RequestBody ProductCreate request) {
        Optional<Category> category = categoryRepository.findById(request.getCategory_id());

        if (category.isPresent()) {
            Product product = new Product(request);
            product.setCategory(category.get());
            ProductResponse productResponse = new ProductResponse(productService.createProduct(product));
            return ResponseEntity.ok(productResponse);
        }

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Добавить изображения продукта")
    @PostMapping(path = "/addImages/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductResponse> addImages(
            @PathVariable String id,
            List<MultipartFile> images
    ) throws IOException {
        Product product = productService.addImages(id, images);

        ProductResponse productResponse = new ProductResponse(product);
        return ResponseEntity.ok(productResponse);
    }


    @Operation(summary = "Удалить")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        productService.deleteProductById(id);
        ResponseEntity.ok().build();
        return null;
    }
}
