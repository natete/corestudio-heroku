package com.OneWingSoft.corestudio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class CorestudioAplication extends SpringBootServletInitializer {

	public static void main(String args[]) {
		SpringApplication.run(CorestudioAplication.class, args);
	}
}
