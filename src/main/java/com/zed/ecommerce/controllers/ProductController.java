package com.zed.ecommerce.controllers;

import com.zed.ecommerce.Dtos.CreateProductDto;
import com.zed.ecommerce.Dtos.ProductDto;
import com.zed.ecommerce.services.ProductService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ProductController {
    private static final Logger logger = LogManager.getLogger(ProductController.class);
    private final ProductService _productService;

    public ProductController(ProductService productService) {
        _productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> index() {
        var products = _productService.findAll();
        return ResponseEntity.ok().body(products);
    }

    @PostMapping("/products")
    public ResponseEntity<Object> store(@Valid @RequestBody CreateProductDto input) {
        try {
            var product = _productService.create(input);
            return ResponseEntity.ok().body(product);
        } catch (Exception e) {
            logger.error("Unexpected error during product creation: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("message", "Failed to create product"));
        }
    }
}
