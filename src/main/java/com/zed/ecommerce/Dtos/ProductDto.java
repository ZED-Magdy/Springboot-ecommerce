package com.zed.ecommerce.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {
    private long id;
    private String title;
    private String description;
    private BigDecimal price;
    private int stock;
}
