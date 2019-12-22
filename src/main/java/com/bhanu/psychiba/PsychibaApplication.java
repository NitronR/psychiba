package com.bhanu.psychiba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PsychibaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PsychibaApplication.class, args);
	}

}
