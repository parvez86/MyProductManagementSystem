package com.example.productmanagementsystem.dto;

import com.example.productmanagementsystem.entity.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private String name;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
//    private List<String> tokens;
}
