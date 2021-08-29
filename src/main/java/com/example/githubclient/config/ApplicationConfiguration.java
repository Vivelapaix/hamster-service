package com.example.githubclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    HamsterBotSettings hamsterBotSettings(AppSettings settings) {
        return settings.getHamsterBotSettings();
    }

}
