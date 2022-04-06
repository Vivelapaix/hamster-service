package com.example.githubclient;

import com.example.githubclient.config.AppSettings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.telegram.telegrambots.ApiContextInitializer;

@Import({
	AppSettings.class,
})
@SpringBootApplication
public class HamsterApplication {

	public static void main(String[] args) {
		//SpringApplication.run(HamsterApplication.class, args);
		ApiContextInitializer.init();

		SpringApplication.run(HamsterApplication.class, args);
	}
}
