package com.lion.skiresortbackend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SkiResortBackendApplication extends SpringBootServletInitializer {
	
	public static final Logger logger = LogManager.getLogger(SkiResortBackendApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SkiResortBackendApplication.class, args);
	}

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SkiResortBackendApplication.class);
    }
}
