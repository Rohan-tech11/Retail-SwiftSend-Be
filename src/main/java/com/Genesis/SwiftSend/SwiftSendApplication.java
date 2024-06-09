package com.Genesis.SwiftSend;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Genesis.SwiftSend.Role.Role;
import com.Genesis.SwiftSend.Role.RoleRepository;
import com.Genesis.SwiftSend.User.User;
import com.Genesis.SwiftSend.User.UserRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class SwiftSendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwiftSendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository,
			UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (roleRepository.findByAuthority("ADMIN").isPresent())
				return;

			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));
			roleRepository.save(new Role("CLIENT"));
			Role userRole = roleRepository.findByAuthority("USER").orElseThrow(
					() -> new RuntimeException("USER role not found"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);
			roles.add(userRole);

			User admin = new User();
			admin.setFullName("SwiftSendAdmin");
			admin.setEmail("johndoe12345@gmail.com");
			admin.setPassword(passwordEncoder.encode("SwiftSendAdmin"));
			admin.setEnabled(true);
			admin.setMobileNumber("1234-6785-345");
			admin.setAuthorities(roles);

			userRepository.save(admin);
			log.info("Admin user created successfully!");
		};
	}

}
