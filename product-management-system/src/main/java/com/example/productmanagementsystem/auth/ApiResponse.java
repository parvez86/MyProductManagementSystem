package com.example.productmanagementsystem.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private int status_code = 200;
    private String message = null;
    private AuthenticationResponse body=null;
}
