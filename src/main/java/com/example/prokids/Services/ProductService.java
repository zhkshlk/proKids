package com.example.prokids.Services;

import com.example.prokids.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    Product findById(String id);
    Product saveProduct(Product product);
    Product createProduct(Product product);
    void deleteProductById(String id);
    Product addImages(String id, List<MultipartFile> images) throws IOException;
    Page<Product> getAllProduct(Double minPrice, Double maxPrice, String category, Pageable pageable);
}
