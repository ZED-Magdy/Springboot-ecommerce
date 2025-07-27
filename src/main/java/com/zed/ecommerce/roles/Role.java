package com.zed.ecommerce.roles;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public abstract class Role {
    public abstract String getName();
    public abstract List<GrantedAuthority> getPermissions();
}
