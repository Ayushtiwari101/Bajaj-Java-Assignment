package com.ayush.bajaj_assignment;

import com.ayush.bajaj_assignment.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BajajAssignmentApplication implements CommandLineRunner {

	@Autowired
	private ApiService apiService;

	public static void main(String[] args) {
		SpringApplication.run(BajajAssignmentApplication.class, args);
	}

	@Override
	public void run(String... args) {
		apiService.executeFlow();
	}
}