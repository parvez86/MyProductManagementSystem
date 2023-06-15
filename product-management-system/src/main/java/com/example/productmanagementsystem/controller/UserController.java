package com.example.productmanagementsystem.controller;

import com.example.productmanagementsystem.dto.UserRequestDTO;
import com.example.productmanagementsystem.dto.UserResponseDTO;
import com.example.productmanagementsystem.entity.User;
import com.example.productmanagementsystem.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins={"http://localhost:3000", "http://localhost:5000"}, allowedHeaders = "*")
@RestController
@RequestMapping("api/v1/user")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
//
    @Autowired
    private UserServiceImpl service;

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<Object> getUsers(){
        System.out.println("Hi");
        List<UserResponseDTO> users = service.getUsers();
        System.out.println(users.size());
        return users.size() != 0 ? ResponseEntity.ok(users):ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<Object> getUserById(@PathVariable("id") Integer id){
        UserResponseDTO user = service.getUserById(id);
        return Objects.nonNull(user)? ResponseEntity.ok(user):ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Object> deleteUserById(@PathVariable("id") Integer id){
        UserResponseDTO user = service.deleteUserById(id);
        return Objects.nonNull(user)? ResponseEntity.ok(user):ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<Object> updateUserById(@PathVariable("id") Integer id, @Valid @RequestBody UserRequestDTO requestDTO){
        UserResponseDTO user = service.updateUserById(id, requestDTO);
        return Objects.nonNull(user)? ResponseEntity.ok(user):ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
