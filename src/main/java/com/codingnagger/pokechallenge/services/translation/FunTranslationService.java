package com.codingnagger.pokechallenge.services.translation;

import com.codingnagger.pokechallenge.model.translation.FunTranslationRequestDto;
import com.codingnagger.pokechallenge.model.translation.FunTranslationResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class FunTranslationService {
    private static final String TRANSLATE_ENDPOINT_FORMAT = "/translate/%s.json";

    private final RestTemplate restTemplate;

    public FunTranslationService(@Qualifier("funTranslationRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<String> translate(FunTranslationLanguage language, String text) {
        FunTranslationRequestDto requestDto = FunTranslationRequestDto.builder()
                .text(text)
                .build();

        String translationUrl = String.format(TRANSLATE_ENDPOINT_FORMAT, language.getRequestPathParameter());

        try {
            ResponseEntity<FunTranslationResponseDto> response = restTemplate.postForEntity(
                    translationUrl, requestDto, FunTranslationResponseDto.class);

            if (Objects.equals(HttpStatus.OK, response.getStatusCode())) {
                return Optional.of(response.getBody().getContents().getTranslation());
            }
        } catch (Throwable t) {
            log.warn("Something went wrong while translating [{}] through endpoint [{}]", text, translationUrl, t);
        }

        return Optional.empty();
    }
}
