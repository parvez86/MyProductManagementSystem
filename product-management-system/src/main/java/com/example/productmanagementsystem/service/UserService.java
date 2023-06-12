package com.example.productmanagementsystem.service;

import com.example.productmanagementsystem.dto.AuthReqDto;
import com.example.productmanagementsystem.dto.UserDto;
import com.example.productmanagementsystem.entity.User;

public interface UserService {
    User getUseByEmail(String email);
    User getUsrById(Integer id);
    User saveUser(UserDto user);
    User updateUser(Integer id, User user);
    User deleteUser(Integer id);

    User authenticate(AuthReqDto requestDto);
}
