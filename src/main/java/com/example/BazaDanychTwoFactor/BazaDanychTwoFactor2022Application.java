package com.example.BazaDanychTwoFactor;

import com.example.BazaDanychTwoFactor.data.Seed;
import com.example.BazaDanychTwoFactor.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BazaDanychTwoFactor2022Application implements CommandLineRunner {

	private UserRepository userRepository;
	public BazaDanychTwoFactor2022Application(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	public static void main(String[] args) {
		SpringApplication.run(BazaDanychTwoFactor2022Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Seed seed = new Seed(userRepository);
		seed.seedData();
	}

}
