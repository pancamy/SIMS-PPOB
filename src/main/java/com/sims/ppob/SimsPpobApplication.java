package com.sims.ppob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SimsPpobApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimsPpobApplication.class, args);
	}

}
