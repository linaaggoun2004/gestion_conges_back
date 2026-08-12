package com.example.gestion_conges_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GestionCongesBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionCongesBackApplication.class, args);
	}

}
