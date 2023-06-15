package com.example.productmanagementsystem.converter;

import com.example.productmanagementsystem.dto.UserRequestDTO;
import com.example.productmanagementsystem.dto.UserResponseDTO;
import com.example.productmanagementsystem.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class UserConverter {
    public List<UserResponseDTO> getUserResponse(List<User> users){
        List<UserResponseDTO> responseDTOS = new ArrayList<>();
        users.forEach((user) -> responseDTOS.add(getUserResponse(user)));
        return responseDTOS;
    }
    public UserResponseDTO getUserResponse(User user){
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        if(Objects.nonNull(user)){
            if(Objects.nonNull(user.getId())) userResponseDTO.setId(user.getId());
            if(Objects.nonNull(user.getName())) userResponseDTO.setName(user.getName());
            if(Objects.nonNull(user.getEmail())) userResponseDTO.setEmail(user.getEmail());
            if(Objects.nonNull(user.getRole())) userResponseDTO.setRole(user.getRole().name());
//            if(user.getTokens().size()>0) userResponseDTO.setTokens(user.getTokens().stream().map((token) -> token.getToken().toString()).toList());
        }
        return userResponseDTO;
    }
}
