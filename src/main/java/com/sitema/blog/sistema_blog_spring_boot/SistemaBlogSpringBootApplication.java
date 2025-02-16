package com.sitema.blog.sistema_blog_spring_boot;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.sitema.blog.sistema_blog_spring_boot")
public class SistemaBlogSpringBootApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(SistemaBlogSpringBootApplication.class, args);
	}

}
