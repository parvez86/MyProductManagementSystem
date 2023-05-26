package com.example.productmanagementsystem.service;

import com.example.productmanagementsystem.dto.AuthReqDto;
import com.example.productmanagementsystem.dto.UserDto;
import com.example.productmanagementsystem.entity.User;

public interface UserService {
    User getUseByEmail(String email);
    User getUsrById(Long id);
    User saveUser(UserDto user);
    User updateUser(Long id, User user);
    User deleteUser(Long id);

    User authenticate(AuthReqDto requestDto);
}
