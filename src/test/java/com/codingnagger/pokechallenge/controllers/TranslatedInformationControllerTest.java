package com.codingnagger.pokechallenge.controllers;

import com.codingnagger.pokechallenge.model.PokemonDto;
import com.codingnagger.pokechallenge.services.PokemonService;
import com.codingnagger.pokechallenge.services.translation.PokemonTranslationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static com.codingnagger.pokechallenge.testutils.FixtureProvider.randomPokemon;
import static com.codingnagger.pokechallenge.testutils.FixtureProvider.randomString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TranslatedInformationControllerTest {
    @Mock
    private PokemonService pokemonService;

    @Mock
    private PokemonTranslationService pokemonTranslationService;

    @InjectMocks
    private TranslatedInformationController controller;

    @Test
    public void getTranslatedPokemonInfo_shouldReturnOkWithTranslatedDto_whenBothServicesReturnDto() {
        String pokemonName = randomString();
        PokemonDto expectedTranslationParameter = randomPokemon();
        PokemonDto expectedBody = randomPokemon();

        doReturn(Optional.of(expectedTranslationParameter)).when(pokemonService).getBasicInformation(anyString());
        doReturn(expectedBody).when(pokemonTranslationService).translate(any(PokemonDto.class));

        ResponseEntity<PokemonDto> actualResponse = controller.getTranslatedPokemonInfo(pokemonName);

        assertThat(actualResponse)
                .isNotNull()
                .satisfies(response -> {
                    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
                    assertThat(response.getBody())
                            .isNotNull()
                            .isEqualTo(expectedBody);
                });

        verify(pokemonService).getBasicInformation(pokemonName);
        verify(pokemonTranslationService).translate(expectedTranslationParameter);
    }

    @Test
    public void getTranslatedPokemonInfo_shouldReturnNotFound_whenPokemonServiceReturnsEmpty() {
        String pokemonName = randomString();
        doReturn(Optional.empty()).when(pokemonService).getBasicInformation(anyString());

        ResponseEntity<PokemonDto> actualResponse = controller.getTranslatedPokemonInfo(pokemonName);

        assertThat(actualResponse)
                .isNotNull()
                .satisfies(response -> {
                    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
                });

        verify(pokemonService).getBasicInformation(pokemonName);
    }
}
