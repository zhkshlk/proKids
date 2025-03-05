package com.example.prokids.specification;

import com.example.prokids.Model.Product;
import org.springframework.data.jpa.domain.Specification;


public class ProductSpecification {
    public static Specification<Product> filterByPrice(Double minPrice, Double maxPrice) {
        return ((root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) {
                return criteriaBuilder.conjunction();
            } else if (minPrice != null && maxPrice != null) {
                return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            }
        });
    }

    public static Specification<Product> filterByCategory(String category) {
        return ((root, query, criteriaBuilder) -> {
            if (category == null  || category.isEmpty())  {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get("category").get("name"), category);
            }
        });
    }
}
