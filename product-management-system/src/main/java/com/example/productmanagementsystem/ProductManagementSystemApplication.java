package com.example.productmanagementsystem;

import com.example.productmanagementsystem.auth.AuthenticationService;
import com.example.productmanagementsystem.auth.RegisterRequest;
import com.example.productmanagementsystem.entity.Role;
import com.example.productmanagementsystem.entity.RoleType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

import static com.example.productmanagementsystem.entity.RoleType.ADMIN;
import static com.example.productmanagementsystem.entity.RoleType.USER;

@SpringBootApplication
public class ProductManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductManagementSystemApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(
//			AuthenticationService service
//	) {
//		return args -> {
//			Role role_user = Role.builder()
//					.name(USER)
//					.build();
//			Role role_admin = Role.builder()
//					.name(ADMIN)
//					.build();
//
//			var user = RegisterRequest.builder()
//					.name("sp")
//					.email("sp86@gmail.com")
//					.password("sp86")
//					.roles(List.of(USER))
//					.build();
//
//			System.out.println("User token: " + service.register(user).getToken());
//
//			var admin = RegisterRequest.builder()
//					.name("Admin")
//					.email("admin@mail.com")
//					.password("password")
//					.roles(List.of(ADMIN, USER))
//					.build();
//
//			System.out.println("Admin token: " + service.register(admin).getToken());
//
//			ObjectMapper mapper = new ObjectMapper();
//			System.out.println("user: "+mapper.writeValueAsString(user));
//			System.out.println("admin: "+mapper.writeValueAsString(admin));
//		};
//	}
}
