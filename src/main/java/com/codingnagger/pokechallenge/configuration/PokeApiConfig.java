package com.codingnagger.pokechallenge.configuration;

import com.codingnagger.pokechallenge.http.client.DefaultRestTemplateErrorHandler;
import com.codingnagger.pokechallenge.model.pokeapi.PokeApiResponseDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Configuration
@ConfigurationProperties(prefix = "poke-api")
@Setter
public class PokeApiConfig {
    public URI uri;

    @Bean
    @Qualifier("pokeApiRestTemplate")
    public RestTemplate pokeApiRestTemplate(DefaultRestTemplateErrorHandler errorHandler, HttpMessageConverter<PokeApiResponseDto> errorConverter) {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(uri.toString())
                .errorHandler(errorHandler)
                .build();

        restTemplate.getMessageConverters().add(errorConverter);

        return restTemplate;
    }
}
