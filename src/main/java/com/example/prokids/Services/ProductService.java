package com.example.prokids.Services;

import com.example.prokids.Model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product findById(String id);
    Product saveProduct(Product product);
    void deleteProductById(String id);
    List<Product> getAllProduct();
}
