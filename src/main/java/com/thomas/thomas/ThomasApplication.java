package com.thomas.thomas;

import com.thomas.thomas.profile.model.Profile;
import com.thomas.thomas.profile.model.Role;
import com.thomas.thomas.profile.service.ProfileService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@SpringBootApplication
public class ThomasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThomasApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ProfileService profileService){
		return args -> {
			profileService.saveRole(new Role(null, "ROLE_USER"));
			profileService.saveRole(new Role(null, "ROLE_MANAGER"));
			profileService.saveRole(new Role(null, "ROLE_ADMIN"));
			profileService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			profileService.saveProfile(new Profile(null, "Thomas Oviedo", "pogiThomas", "12345", new ArrayList<>()));
			profileService.saveProfile(new Profile(null, "Francisco Ramos", "pangetFrancis", "12345", new ArrayList<>()));

			profileService.addRoleToProfile("pogiThomas", "ROLE_SUPER_ADMIN");
			profileService.addRoleToProfile("pangetFrancis", "ROLE_USER");
		};
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

}
