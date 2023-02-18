package com.toyprj.toyman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@EnableJpaAuditing // JPA Auditing 활성화
@SpringBootApplication
public class ToymanApplication {

	public static void main(String[] args) {
		System.out.println("Hello, World!");
		SpringApplication.run(ToymanApplication.class, args);
	}
}
