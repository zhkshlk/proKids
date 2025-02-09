package com.example.prokids.dto;

import com.example.prokids.Model.Category;
import com.example.prokids.Model.Product;
import com.example.prokids.Model.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private String id;

    private String name;

    private String description;

    private double price;

    private String category;

    private List<String> images;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.category = product.getCategory().getName();
        this.images = product.getImages().stream().map(ProductImage::getImagePath).toList();
    }
}