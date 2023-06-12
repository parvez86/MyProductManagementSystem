package com.example.productmanagementsystem.service;

import com.example.productmanagementsystem.dto.AuthReqDto;
import com.example.productmanagementsystem.dto.UserDto;
import com.example.productmanagementsystem.entity.User;
import com.example.productmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository repository;
    @Override
    public User getUseByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    @Override
    public User getUsrById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public User saveUser(UserDto userDto) {
        if(!userDto.getPassword().equals(userDto.getConfirm_password())){
            System.out.println("password mismatch");
            return null;
        }
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return repository.save(user);
    }

    @Override
    public User updateUser(Integer id, User user) {
        User new_user = repository.findById(id).orElse(null);
        if(Objects.nonNull(new_user)){
            new_user.setName(user.getName());
            new_user.setEmail(user.getEmail());
            new_user.setPassword(user.getPassword());
            repository.save(new_user);
        }
        return new_user;
    }

    @Override
    public User deleteUser(Integer id) {
        User user = repository.findById(id).orElse(null);
        if(Objects.nonNull(user)){
            repository.delete(user);
        }
        return user;
    }
    

    @Override
    public User authenticate(AuthReqDto requestDto) {
        System.out.println("Request dto: "+ requestDto);
        User user = repository.findByEmailAndPassword(requestDto.getEmail(), requestDto.getPassword()).orElse(null);
        if(Objects.nonNull(user)){
            System.out.println("Authentication");
            System.out.println("User: "+ user);
            return user;
        }
        return null;
    }
}
