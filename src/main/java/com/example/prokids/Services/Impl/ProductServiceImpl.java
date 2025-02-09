package com.example.prokids.Services.Impl;

import com.example.prokids.Model.Product;
import com.example.prokids.Model.ProductImage;
import com.example.prokids.Services.ProductService;
import com.example.prokids.repositories.ImageRepository;
import com.example.prokids.repositories.ProductRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Data
@RequiredArgsConstructor

public class ProductServiceImpl implements ProductService {
    private static final String UPLOAD_DIR = "uploads/images/products";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;


    @Override
    public Product findById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product addImages(String id, List<MultipartFile> images) throws IOException {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                List<ProductImage> imagePaths = new ArrayList<>();
                for (MultipartFile file : images) {
                    String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                    Path path = Paths.get(UPLOAD_DIR, filename);
                    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    ProductImage productImage = new ProductImage();
                    productImage.setProduct(product);
                    productImage.setImagePath("/uploads/images/products/" + filename);
                    imagePaths.add(productImage);
                }

                imageRepository.saveAll(imagePaths);

                return product;
            }
        } catch (IOException e) {
            throw new IOException();
        }
        return null;
    }

    @Override
    public void deleteProductById(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }
}
