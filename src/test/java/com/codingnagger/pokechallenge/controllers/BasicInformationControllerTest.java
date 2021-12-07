package com.codingnagger.pokechallenge.controllers;

import com.codingnagger.pokechallenge.model.PokemonResponseDto;
import com.codingnagger.pokechallenge.services.PokemonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static com.codingnagger.pokechallenge.testutils.FixtureProvider.randomPokemonResponse;
import static com.codingnagger.pokechallenge.testutils.FixtureProvider.randomString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BasicInformationControllerTest {
    @Mock
    private PokemonService pokemonService;

    @InjectMocks
    private BasicInformationController controller;

    @Test
    public void getPokemonInfo_shouldReturnOkWithDto_whenServiceReturnsDto() {
        String pokemonName = randomString();
        PokemonResponseDto expectedBody = randomPokemonResponse();
        doReturn(Optional.of(expectedBody)).when(pokemonService).getBasicInformation(anyString());

        ResponseEntity<PokemonResponseDto> actualResponse = controller.getPokemonInfo(pokemonName);

        assertThat(actualResponse)
                .isNotNull()
                .satisfies(response -> {
                    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
                    assertThat(response.getBody())
                            .isNotNull()
                            .isEqualTo(expectedBody);
                });

        verify(pokemonService).getBasicInformation(pokemonName);
    }

    @Test
    public void getPokemonInfo_shouldReturnNotFound_whenServiceReturnsEmpty() {
        String pokemonName = randomString();
        doReturn(Optional.empty()).when(pokemonService).getBasicInformation(anyString());

        ResponseEntity<PokemonResponseDto> actualResponse = controller.getPokemonInfo(pokemonName);

        assertThat(actualResponse)
                .isNotNull()
                .satisfies(response -> {
                    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
                });

        verify(pokemonService).getBasicInformation(pokemonName);
    }
}
