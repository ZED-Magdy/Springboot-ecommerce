package com.zed.ecommerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private int price;
    private int stock;

    public BigDecimal getPrice() {
        return BigDecimal.valueOf(price)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    public void setPrice(BigDecimal price) {
        BigDecimal multiplied = price.multiply(BigDecimal.valueOf(100));
        this.price = multiplied.intValue();
    }
}
