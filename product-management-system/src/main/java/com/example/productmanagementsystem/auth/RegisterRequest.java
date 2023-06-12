package com.example.productmanagementsystem.auth;


import com.example.productmanagementsystem.entity.Role;
import com.example.productmanagementsystem.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private List<RoleType> roles;
}
