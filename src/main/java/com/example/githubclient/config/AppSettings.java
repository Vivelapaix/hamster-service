package com.example.githubclient.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
@ConfigurationProperties
public class AppSettings {

    private HamsterBotSettings hamsterBotSettings;

    public HamsterBotSettings getHamsterBotSettings() {
        return hamsterBotSettings;
    }

    public void setHamsterBotSettings(HamsterBotSettings hamsterBotSettings) {
        this.hamsterBotSettings = hamsterBotSettings;
    }
}