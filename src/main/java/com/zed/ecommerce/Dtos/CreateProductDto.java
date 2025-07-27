package com.zed.ecommerce.Dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateProductDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    @Min(0)
    private BigDecimal price;
    @NotBlank
    @Min(0)
    private int stock;
}
