package com.example.esp33_frontEndApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Esp33FrontEndAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(Esp33FrontEndAppApplication.class, args);
	}


	@Bean
    	public RestTemplate restTemplate(RestTemplateBuilder builder) {
        	return builder.build();
    	}
}
