package com.zed.ecommerce.roles;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class UserRole extends Role {
    @Override
    public String getName() {
        return "USER";
    }

    @Override
    public List<GrantedAuthority> getPermissions() {
        return List.of((GrantedAuthority) () -> "PRODUCT_READ");
    }
}
