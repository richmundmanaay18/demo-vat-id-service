package com.example;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DemoVatIdServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoVatIdServiceApplication.class, args);
	}

	@PostConstruct
	public void printRedisConfig() {
		System.out.println("🔍 REDIS_URL = " + System.getenv("REDIS_URL"));
	}
}
