package com.example.productmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
//			var user = RegisterReque                   st.builder()
//					.name("sp")
//					.email("sp86@gmail.com")
//					.password("sp86")
//					.role(USER)
//					.build();
//			AuthenticationResponse response = service.register(user);
//			System.out.println("User token: " + ((Objects.nonNull(response)?response.getToken():"null")));
//
//			var admin = RegisterRequest.builder()
//					.name("Admin")
//					.email("admin@gmail.com")
//					.password("password")
//					.role(ADMIN)
//					.build();
//
//			System.out.println("Admin token: " + ((Objects.nonNull(response)?response.getToken():"null")));
//
//			ObjectMapper mapper = new ObjectMapper();
//			System.out.println("user: "+mapper.writeValueAsString(user));
//			System.out.println("admin: "+mapper.writeValueAsString(admin));
//		};
//	}
}
