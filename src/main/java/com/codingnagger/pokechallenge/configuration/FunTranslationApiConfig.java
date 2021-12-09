package com.codingnagger.pokechallenge.configuration;

import com.codingnagger.pokechallenge.http.client.DefaultRestTemplateErrorHandler;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

@Configuration
@ConfigurationProperties(prefix = "funtranslation-api")
@Setter
public class FunTranslationApiConfig {
    public URI uri;

    @Bean
    @Qualifier("funTranslationRestTemplate")
    public RestTemplate funTranslationRestTemplate(DefaultRestTemplateErrorHandler errorHandler) {
        return new RestTemplateBuilder()
                .rootUri(uri.toString())
                .errorHandler(errorHandler)
                .build();
    }
}
