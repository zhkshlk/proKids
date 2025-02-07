package com.example.prokids.controllers;

import com.example.prokids.Model.Product;
import com.example.prokids.Services.CategoryService;
import com.example.prokids.Services.ProductService;
import com.example.prokids.repositories.ProductRepository;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/{id}/image")
    public ResponseEntity<String> getProductImage(
            @Parameter(description = "ID продукта", required = true) @PathVariable String id
    ) {
        Product product = productService.findById(id);
        if (product != null && product.getImage() != null) {
            String base64Image = product.getImage();
            return new ResponseEntity<>(base64Image, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("new_product", new Product());
        model.addAttribute("all_categories", categoryService.getAllCategory());
        return "create-product";
    }

    @PostMapping("/create")
    public String createProduct(
            @Parameter(description = "Данные продукта", required = true) @ModelAttribute Product product,
            @Parameter(description = "Изображение продукта", required = true) @RequestParam("imageFile") MultipartFile imageFile
    ) throws IOException {
        if (!imageFile.isEmpty()) {
            byte[] imageBytes = imageFile.getBytes();
            String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
            product.setImage(encodedImage);
        }
        productService.saveProduct(product);
        return "redirect:/products/create";
    }
    @GetMapping("/products")
    public String getAllProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("product/{id}")
    public String getProduct (@PathVariable("id") String id, Model model) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            model.addAttribute("product", product);
            return "product";
        }
        else {
            return "productNotFound";
        }
    }
}
