package com.example.productmanagementsystem.auth;


import com.example.productmanagementsystem.config.JwtService;
import com.example.productmanagementsystem.entity.Token;
import com.example.productmanagementsystem.entity.TokenType;
import com.example.productmanagementsystem.entity.User;
import com.example.productmanagementsystem.repository.TokenRepository;
import com.example.productmanagementsystem.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ApiResponse register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        System.out.println("register user: "+ user);
//        System.out.println(repository.findByEmail(user.getEmail()));
        if(!repository.findByEmail(user.getEmail()).isEmpty()){
            return ApiResponse.builder()
                    .status_code(400)
                    .message("User Already Exist")
                    .build();
        }
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        System.out.println(jwtToken);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
        return ApiResponse.builder()
                .status_code(200)
                .body(authenticationResponse)
                .build();
    }

    public ApiResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail()).orElse(null);
        if(Objects.isNull(user)) {
            return ApiResponse.builder()
                    .status_code(404)
                    .message("User not found!")
                    .build();
        }
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        System.out.println("token: "+jwtToken);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
        return ApiResponse.builder()
                .status_code(200)
                .body(authenticationResponse)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public ApiResponse refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ApiResponse.builder()
                    .status_code(400)
                    .message("Invalid token")
                    .build();
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
            return ApiResponse.builder()
                    .status_code(200)
                    .message("Successfully user token refreshed")
                    .build();
        }
        return ApiResponse.builder()
                .status_code(400)
                .message("Invalid user")
                .build();
    }
}


//public class AuthenticationService {
//    private final UserRepository repository;
//    private final TokenRepository tokenRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtService jwtService;
//    private final AuthenticationManager authenticationManager;
//
//    public AuthenticationResponse register(RegisterRequest request) {
//        var user = User.builder()
//                .name(request.getName())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(request.getRole())
//                .build();
//        System.out.println("register user: "+ user);
////        System.out.println(repository.findByEmail(user.getEmail()));
//        if(!repository.findByEmail(user.getEmail()).isEmpty()){
//            return null;
//        }
//        var savedUser = repository.save(user);
//        var jwtToken = jwtService.generateToken(user);
//        var refreshToken = jwtService.generateRefreshToken(user);
//        saveUserToken(savedUser, jwtToken);
//        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
//        return authenticationResponse;
//    }
//
//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),
//                        request.getPassword()
//                )
//        );
//        var user = repository.findByEmail(request.getEmail()).orElse(null);
//        if(Objects.isNull(user)) {
//            return AuthenticationResponse.builder()
//                    .token(null)
//                    .build();
//        }
//        var existValidToken = getExitingValidToken(user);
//        if(Objects.nonNull(existValidToken)){
//            return AuthenticationResponse.builder()
//                    .token(existValidToken.getToken())
//                    .build();
//        }
//        var jwtToken = jwtService.generateToken(user);
//        System.out.println("generated token: "+jwtToken);
//        var refreshToken = jwtService.generateRefreshToken(user);
//        System.out.println("refresh token: "+refreshToken);
//        revokeAllUserTokens(user);
//        saveUserToken(user, jwtToken);
//
//        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
//        return authenticationResponse;
//    }
//
//    private Token getExitingValidToken(User user) {
//        var token = tokenRepository.findAllValidTokenByUser(user.getId());
//        if(Objects.nonNull(token)){
//            return token.get(0);
//        }
//        return null;
//    }
//
//    private void saveUserToken(User user, String jwtToken) {
//        var token = Token.builder()
//                .user(user)
//                .token(jwtToken)
//                .tokenType(TokenType.BEARER)
//                .expired(false)
//                .revoked(false)
//                .build();
//        tokenRepository.save(token);
//    }
//
//    private void revokeAllUserTokens(User user) {
////        System.out.println("user: "+user.toString());
//        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
////        var validUserTokens = tokenRepository.findByRevokedOrExpired(false, false);
////        System.out.println("tokens: "+ (validUserTokens != null? "not null":"null"));
////        validUserTokens = validUserTokens.stream().filter(
////                m -> {
////                    return m.getId() == user.getId();
////                }
////        ).collect(Collectors.toList());
//        if (validUserTokens.isEmpty())
//            return;
//        validUserTokens.forEach(token -> {
//            token.setExpired(true);
//            token.setRevoked(true);
//        });
//        tokenRepository.saveAll(validUserTokens);
//    }
//
//    public AuthenticationResponse refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException {
//        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        final String refreshToken;
//        final String userEmail;
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            return AuthenticationResponse
//                    .builder()
//                    .token(null)
//                    .build();
//        }
//        refreshToken = authHeader.substring(7);
//        userEmail = jwtService.extractUsername(refreshToken);
//        if (userEmail != null) {
//            var user = this.repository.findByEmail(userEmail)
//                    .orElseThrow();
//            if (jwtService.isTokenValid(refreshToken, user)) {
//                var accessToken = jwtService.generateToken(user);
//                revokeAllUserTokens(user);
//                saveUserToken(user, accessToken);
//                var authResponse = AuthenticationResponse.builder()
//                        .token(accessToken)
//                        .build();
//                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
//            }
//            return AuthenticationResponse.builder()
//                    .token(refreshToken)
//                    .build();
//        }
//        return AuthenticationResponse.builder()
//                .token(null)
//                .build();
//    }
//}
