package com.codingnagger.pokechallenge.services;

import com.codingnagger.pokechallenge.model.PokemonDto;
import com.codingnagger.pokechallenge.services.translation.FunTranslationLanguage;
import com.codingnagger.pokechallenge.services.translation.FunTranslationService;
import com.codingnagger.pokechallenge.services.translation.PokemonTranslationService;
import com.codingnagger.pokechallenge.testutils.FixtureProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.codingnagger.pokechallenge.testutils.FixtureProvider.randomString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PokemonTranslationServiceTest {
    @Mock
    private FunTranslationService funTranslationService;

    @InjectMocks
    private PokemonTranslationService pokemonTranslationService;

    @Test
    public void translate_shouldAlwaysTranslateTheDescription() {
        PokemonDto pokemonDto = FixtureProvider.randomPokemon();

        pokemonTranslationService.translate(pokemonDto);

        verify(funTranslationService).translate(any(FunTranslationLanguage.class), eq(pokemonDto.getDescription()));
    }

    @Test
    public void translate_shouldReturnPokemonWithTranslation_whenTranslationServiceReturnsTranslation() {
        String expectedDescription = randomString();
        PokemonDto pokemonDto = FixtureProvider.randomPokemon();

        doReturn(Optional.of(expectedDescription))
                .when(funTranslationService).translate(any(FunTranslationLanguage.class), any(String.class));

        PokemonDto result = pokemonTranslationService.translate(pokemonDto);

        assertThat(result).isNotNull()
                .satisfies(p -> {
                    assertThat(p.getDescription()).isEqualTo(expectedDescription);
                    assertThat(p.getName()).isEqualTo(pokemonDto.getName());
                    assertThat(p.getHabitat()).isEqualTo(pokemonDto.getHabitat());
                    assertThat(p.isLegendary()).isEqualTo(pokemonDto.isLegendary());
                });
    }

    @Test
    public void translate_shouldReturnPokemonWithOriginalDescription_whenTranslationServiceDoesntReturnTranslation() {
        PokemonDto pokemonDto = FixtureProvider.randomPokemon();

        doReturn(Optional.empty())
                .when(funTranslationService).translate(any(FunTranslationLanguage.class), any(String.class));

        PokemonDto result = pokemonTranslationService.translate(pokemonDto);

        assertThat(result)
                .isNotNull()
                .isEqualTo(pokemonDto);
    }

    @Test
    public void translate_shouldTranslateUsingYodaLanguage_whenPokemonIsLegendaryAndHabitatIsNotCave() {
        PokemonDto pokemonDto = PokemonDto.builder()
                .isLegendary(true)
                .description(randomString())
                .build();

        pokemonTranslationService.translate(pokemonDto);

        verify(funTranslationService).translate(eq(FunTranslationLanguage.YODA), any());
    }

    @Test
    public void translate_shouldTranslateUsingYodaLanguage_whenPokemonIsNotLegendaryAndHabitatIsCave() {
        PokemonDto pokemonDto = PokemonDto.builder()
                .isLegendary(false)
                .habitat("cave")
                .build();

        pokemonTranslationService.translate(pokemonDto);

        verify(funTranslationService).translate(eq(FunTranslationLanguage.YODA), any());
    }

    @Test
    public void translate_shouldTranslateUsingShakespeareLanguage_whenPokemonIsNotLegendaryAndHabitatIsNotCave() {
        PokemonDto pokemonDto = PokemonDto.builder()
                .isLegendary(false)
                .habitat(randomString())
                .build();

        pokemonTranslationService.translate(pokemonDto);

        verify(funTranslationService).translate(eq(FunTranslationLanguage.SHAKESPEARE), any());
    }
}
