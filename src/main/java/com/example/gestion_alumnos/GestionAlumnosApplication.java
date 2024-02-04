package com.example.gestion_alumnos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication
@EnableFeignClients("com.example.gestion_alumnos")
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class GestionAlumnosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionAlumnosApplication.class, args);
	}

}
