package com.zed.ecommerce.roles;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class AdminRole extends  Role {
    @Override
    public String getName() {
        return "ADMIN";
    }

    @Override
    public List<GrantedAuthority> getPermissions() {
        return List.of((GrantedAuthority) () -> "PRODUCT_READ", (GrantedAuthority) () -> "PRODUCT_WRITE");
    }
}
