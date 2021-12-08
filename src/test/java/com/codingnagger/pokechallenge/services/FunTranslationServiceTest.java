package com.codingnagger.pokechallenge.services;

import com.codingnagger.pokechallenge.model.translation.FunTranslationContentsDto;
import com.codingnagger.pokechallenge.model.translation.FunTranslationRequestDto;
import com.codingnagger.pokechallenge.model.translation.FunTranslationResponseDto;
import com.codingnagger.pokechallenge.services.translation.FunTranslationLanguage;
import com.codingnagger.pokechallenge.services.translation.FunTranslationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.codingnagger.pokechallenge.testutils.FixtureProvider.randomFunTranslationLanguage;
import static com.codingnagger.pokechallenge.testutils.FixtureProvider.randomString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FunTranslationServiceTest {
    public static final String FUN_TRANSLATION_YODA_ENDPOINT = "/translate/yoda.json";
    public static final String FUN_TRANSLATION_SHAKESPEARE_ENDPOINT = "/translate/shakespeare.json";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FunTranslationService service;

    @Test
    public void translate_shouldReturnTranslation_whenApiReturnsTranslation() {
        String expectedTranslation = randomString();

        FunTranslationResponseDto expectedResponse = FunTranslationResponseDto.builder()
                .contents(FunTranslationContentsDto.builder().translation(expectedTranslation).build())
                .build();

        doReturn(ResponseEntity.ok(expectedResponse))
                .when(restTemplate)
                .postForEntity(anyString(), any(FunTranslationRequestDto.class), eq(FunTranslationResponseDto.class));

        Optional<String> translation = service.translate(randomFunTranslationLanguage(), randomString());

        assertThat(translation).isNotEmpty().hasValue(expectedTranslation);
    }

    @Test
    public void translate_shouldReturnEmpty_whenApiReturnsDoesntReturnTranslation() {
        doReturn(ResponseEntity.notFound().build())
                .when(restTemplate)
                .postForEntity(anyString(), any(FunTranslationRequestDto.class), eq(FunTranslationResponseDto.class));

        Optional<String> translation = service.translate(randomFunTranslationLanguage(), randomString());

        assertThat(translation).isEmpty();
    }

    @Test
    public void translate_shouldReturnEmpty_whenApiCallTriggersException() {
        doThrow(new RuntimeException(randomString()))
                .when(restTemplate)
                .postForEntity(anyString(), any(FunTranslationRequestDto.class), eq(FunTranslationResponseDto.class));

        Optional<String> translation = service.translate(randomFunTranslationLanguage(), randomString());

        assertThat(translation).isEmpty();
    }

    @Test
    public void translate_shouldUseShakespearUrl_whenUsingShakespeareLanguage() {
        service.translate(FunTranslationLanguage.SHAKESPEARE, randomString());

        verify(restTemplate).postForEntity(eq(FUN_TRANSLATION_SHAKESPEARE_ENDPOINT), any(), any());
    }

    @Test
    public void translate_shouldUseYodaUrl_whenUsingYodaLanguage() {
        service.translate(FunTranslationLanguage.YODA, randomString());

        verify(restTemplate).postForEntity(eq(FUN_TRANSLATION_YODA_ENDPOINT), any(), any());
    }
}
