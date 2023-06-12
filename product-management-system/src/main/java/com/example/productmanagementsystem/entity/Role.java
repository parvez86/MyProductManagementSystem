package com.example.productmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class Role {
    @Id
//    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoleType name;
}
