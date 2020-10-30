package br.com.senac.servicestecinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EntityScan(basePackages = "br.com.senac.servicestecinfo")
@ComponentScan(basePackages = "br.com.senac.servicestecinfo.model")
public class ServicesTecinfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicesTecinfoApplication.class, args);
	}

}
