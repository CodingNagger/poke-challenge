package com.codingnagger.pokechallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class PokeChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PokeChallengeApplication.class, args);
    }

}
