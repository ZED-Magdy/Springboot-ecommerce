package com.zed.ecommerce.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginUserDto {
    @NotBlank
    @Pattern(message = "Phone number must start with 010|011|012|015 and be 11 digits long", regexp = "^(010|011|012|015)\\d{8}$")
    private String phoneNumber;

    @NotBlank
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;
}
