package com.codingnagger.pokechallenge.configuration;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Configuration
@ConfigurationProperties(prefix = "poke-api")
@Setter
public class PokeApiConfig {
    public URI uri;

    @Bean
    @Qualifier("pokeApiRestTemplate")
    public RestTemplate pokeApiRestTemplate() {
        return new RestTemplateBuilder().rootUri(uri.toString()).build();
    }
}
