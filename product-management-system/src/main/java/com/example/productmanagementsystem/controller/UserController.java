package com.example.productmanagementsystem.controller;

import com.example.productmanagementsystem.dto.UserDto;
import com.example.productmanagementsystem.dto.AuthReqDto;
import com.example.productmanagementsystem.entity.User;
import com.example.productmanagementsystem.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1")
public class UserController {
    @Autowired
    private UserServiceImpl service;

    @PostMapping("/users")
    ResponseEntity<Object> saveUser(@Valid @RequestBody UserDto userDto){
        User user = service.saveUser(userDto);
        return Objects.nonNull(user) ? ResponseEntity.ok(user):ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Inavalid user credentials");
    }

    @PostMapping("/authenticate")
    ResponseEntity<Object> authenticate(@Valid @RequestBody AuthReqDto requestDto){
        User user = service.authenticate(requestDto);
        return Objects.nonNull(user) ? ResponseEntity.ok(user):ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid user");
    }
}
