package com.example.productmanagementsystem.repository;

import com.example.productmanagementsystem.entity.Role;
import com.example.productmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByRole(Role role);
    Optional<User> findByEmailAndPassword(String email, String Password);
    List<User> findAll();
}
