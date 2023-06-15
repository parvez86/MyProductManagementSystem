package com.example.productmanagementsystem.service;

import com.example.productmanagementsystem.dto.UserRequestDTO;
import com.example.productmanagementsystem.dto.UserResponseDTO;
import com.example.productmanagementsystem.entity.User;

import java.util.List;

public interface UserService {
    User getUserByEmail(String email);
    UserResponseDTO updateUserById(Integer id, UserRequestDTO user);

    List<UserResponseDTO> getUsers();

    UserResponseDTO getUserById(Integer id);

    UserResponseDTO deleteUserById(Integer id);
}
