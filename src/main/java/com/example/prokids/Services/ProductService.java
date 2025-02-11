package com.example.prokids.Services;

import com.example.prokids.Model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product findById(String id);
    Product saveProduct(Product product);
    Product createProduct(Product product);
    void deleteProductById(String id);
    List<Product> getAllProduct();
    Product addImages(String id, List<MultipartFile> images) throws IOException;
}
