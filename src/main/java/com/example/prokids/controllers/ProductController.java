package com.example.prokids.controllers;

import com.example.prokids.Model.Category;
import com.example.prokids.Model.Product;
import com.example.prokids.Services.Impl.ProductServiceImpl;
import com.example.prokids.dto.ProductCreate;
import com.example.prokids.dto.ProductResponse;
import com.example.prokids.repositories.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Tag(name = "Товары")
public class ProductController {
    private final ProductServiceImpl productService;
    private final CategoryRepository categoryRepository;

    @Operation(summary = "Создать")
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductResponse> create(@RequestBody ProductCreate request) {
        Optional<Category> category = categoryRepository.findById(request.getCategory_id());
        if (category.isPresent()) {
            System.out.println(category.get().getId());
        } else {
            System.out.println("Not found");
        }
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

    @Operation(summary = "Продукты")
    @GetMapping()
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> products = productService.getAllProduct();

        List<ProductResponse> productResponses = products.stream().map(ProductResponse::new).toList();

        return ResponseEntity.ok(productResponses);
    }

    @Operation(summary = "Продукт")
    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ProductResponse> getProduct (@PathVariable("id") String id) {
        Product product = productService.findById(id);
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
