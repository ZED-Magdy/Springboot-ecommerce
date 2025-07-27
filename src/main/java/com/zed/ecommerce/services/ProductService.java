package com.zed.ecommerce.services;

import com.zed.ecommerce.Dtos.CreateProductDto;
import com.zed.ecommerce.Dtos.ProductDto;
import com.zed.ecommerce.models.Product;
import com.zed.ecommerce.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository _productRepository;

    public ProductService(ProductRepository productRepository) {
        _productRepository = productRepository;
    }


    public List<ProductDto> findAll() {
        return _productRepository.findAll().stream().map(p -> new ProductDto()).toList();
    }

    public ProductDto create(CreateProductDto input) {
        var product = new Product();
        product.setTitle(input.getTitle());
        product.setDescription(input.getDescription());
        product.setPrice(input.getPrice());
        product.setStock(input.getStock());

        var savedProduct = _productRepository.save(product);


        return new ProductDto(
                savedProduct.getId(),
                savedProduct.getTitle(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getStock()
        );
    }
}
