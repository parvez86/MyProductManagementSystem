package com.example.productmanagementsystem;

import com.example.productmanagementsystem.auth.AuthenticationResponse;
import com.example.productmanagementsystem.auth.AuthenticationService;
import com.example.productmanagementsystem.auth.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

import static com.example.productmanagementsystem.entity.Role.ADMIN;
import static com.example.productmanagementsystem.entity.Role.USER;

@SpringBootApplication
public class ProductManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductManagementSystemApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			var user = RegisterRequest.builder()
					.name("sp")
					.email("sp86@gmail.com")
					.password("sp86")
					.role(USER)
					.build();
			AuthenticationResponse response = service.register(user).getBody();
			System.out.println("User token: " + ((Objects.nonNull(response)?response.getAccessToken():"null")));

			var admin = RegisterRequest.builder()
					.name("Admin")
					.email("admin@" +
							"mail.com")
					.password("password")
					.role(ADMIN)
					.build();
			response = service.register(admin).getBody();
			System.out.println("Admin token: " + ((Objects.nonNull(response)?response.getAccessToken():"null")));

			ObjectMapper mapper = new ObjectMapper();
			System.out.println("user: "+mapper.writeValueAsString(user));
			System.out.println("admin: "+mapper.writeValueAsString(admin));
		};
	}
}
