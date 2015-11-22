package com.onewingsoft.corestudio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.TimeZone;

@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class Aplication extends SpringBootServletInitializer {

	public static void main(String args[]) {
		SpringApplication.run(Aplication.class, args);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Madrid"));
	}
}
