package com.codingnagger.pokechallenge.configuration;

import com.codingnagger.pokechallenge.http.client.DefaultRestTemplateErrorHandler;
import com.codingnagger.pokechallenge.http.client.NopPlainTextHttpMessageConverter;
import com.codingnagger.pokechallenge.model.pokeapi.PokeApiResponseDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

@Configuration
public class RestTemplateConfig {
    @Bean
    public DefaultRestTemplateErrorHandler responseErrorHandler() {
        return new DefaultRestTemplateErrorHandler();
    }

    @Bean
    public HttpMessageConverter<PokeApiResponseDto> pokeApiPlainTextMessageConverter() {
        return new NopPlainTextHttpMessageConverter<>();
    }
}
