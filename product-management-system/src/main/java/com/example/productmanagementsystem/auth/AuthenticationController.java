package com.example.productmanagementsystem.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

@RestController
@CrossOrigin(origins = {"http://localhost:3000/", "http://localhost:5000"})
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        System.out.println(request);
//        ObjectMapper mapper = new ObjectMapper();
        ApiResponse response = service.register(request);
        return  response.getStatus_code()==200 ? ResponseEntity.ok(response.getBody()):ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getMessage());
    }
    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        ApiResponse  response = service.authenticate(request);
        System.out.println(response.toString());
        return response.getStatus_code()==200 ? ResponseEntity.ok(response.getBody()):ResponseEntity.status(HttpStatus.FORBIDDEN).body(response.getMessage());
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Object> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        ApiResponse apiResponse = service.refreshToken(request, response);
        return apiResponse.getStatus_code()==200 ? ResponseEntity.ok(apiResponse.getMessage()):ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponse.getMessage());
    }
}
