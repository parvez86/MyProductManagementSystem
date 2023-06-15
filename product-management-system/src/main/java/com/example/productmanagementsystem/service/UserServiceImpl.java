package com.example.productmanagementsystem.service;

import com.example.productmanagementsystem.converter.UserConverter;
import com.example.productmanagementsystem.dto.UserRequestDTO;
import com.example.productmanagementsystem.dto.UserResponseDTO;
import com.example.productmanagementsystem.entity.User;
import com.example.productmanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository repository;
    private final UserConverter converter;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User getUserByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }
    @Override
    public List<UserResponseDTO> getUsers() {
        System.out.println("Hi service");
        List<User> users = repository.findAll();
        System.out.println(users.size());
        return converter.getUserResponse(users);
    }

    @Override
    public UserResponseDTO getUserById(Integer id) {
        User user = repository.findById(id).orElse(null);
        if (Objects.nonNull(user)){
            return converter.getUserResponse(user);
        }
        return null;
    }

    @Override
    public UserResponseDTO updateUserById(Integer id, UserRequestDTO userRequestDTO) {
        User user = repository.findById(id).orElse(null);
        if(Objects.nonNull(user)){
            user.setName(userRequestDTO.getName());
            user.setEmail(userRequestDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
            user.setRole(userRequestDTO.getRole());
            repository.save(user);
            return converter.getUserResponse(user);
        }
        return null;
    }

    @Override
    public UserResponseDTO deleteUserById(Integer id) {
        User user = repository.findById(id).orElse(null);
        if(Objects.nonNull(user)){
            repository.delete(user);
            return converter.getUserResponse(user);
        }
        return null;
    }
}
