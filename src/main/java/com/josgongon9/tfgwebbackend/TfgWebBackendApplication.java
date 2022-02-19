package com.josgongon9.tfgwebbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class TfgWebBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TfgWebBackendApplication.class, args);
	}

	@Bean
	public ReloadableResourceBundleMessageSource messageSource(){
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setFallbackToSystemLocale(Boolean.FALSE);
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
		messageSource.setBasename("classpath:locale/messages");
		return messageSource;
	}

}
