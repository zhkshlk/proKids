package com.example.prokids.dto;

import com.example.prokids.Model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreate {
    private String name;

    private String description;

    private double price;

    private String category_id;
}