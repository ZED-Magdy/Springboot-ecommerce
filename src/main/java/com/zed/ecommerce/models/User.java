package com.zed.ecommerce.models;

import com.zed.ecommerce.roles.AdminRole;
import com.zed.ecommerce.roles.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private  long id;
    @Column(nullable = false, unique = true, length = 100)
    private   String email;
    @Column(nullable = false, length = 100)
    private  String password;
    @Column(nullable = false, length = 100)
    private  String firstName;
    @Column(nullable = false, length = 100)
    private  String lastName;
    @Column(nullable = false, length = 15, unique = true)
    private  String phoneNumber;
    @Column()
    private  String address;
    @Column()
    private  String birthDate;
    @Column()
    private String role; // e.g., "USER", "ADMIN"
    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;
    @UpdateTimestamp
    @Column()
    private Date updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(role.equals(new AdminRole().getName())) {
            return new AdminRole().getPermissions();
        }
        return new UserRole().getPermissions();
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
