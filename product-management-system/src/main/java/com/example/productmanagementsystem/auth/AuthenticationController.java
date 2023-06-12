package com.example.productmanagementsystem.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        System.out.println(request);
//        ObjectMapper mapper = new ObjectMapper();
        AuthenticationResponse response = service.register(request);
        return  Objects.nonNull(response.getToken()) ? ResponseEntity.ok(response):ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse  response = service.authenticate(request);
        return Objects.nonNull(response)? ResponseEntity.ok(response):ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Object> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        AuthenticationResponse authResponse = service.refreshToken(request, response);
        return Objects.nonNull(authResponse.getToken())? ResponseEntity.ok(authResponse):ResponseEntity.status(HttpStatus.FORBIDDEN).body(authResponse);
    }
}
