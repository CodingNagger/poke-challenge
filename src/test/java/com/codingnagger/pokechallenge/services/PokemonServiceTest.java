package com.codingnagger.pokechallenge.services;


import com.codingnagger.pokechallenge.model.PokemonResponseDto;
import com.codingnagger.pokechallenge.model.pokeapi.PokeApiResponseDto;
import com.codingnagger.pokechallenge.testutils.FixtureProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.codingnagger.pokechallenge.services.PokemonService.LANGUAGE_ENGLISH;
import static com.codingnagger.pokechallenge.testutils.FixtureProvider.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTest {
    public static final String POKE_API_V2_SPECIES_ENDPOINT = "/v2/pokemon-species/";
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PokemonService service;

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void getBasicInformation_shouldReturnPokemonLegendaryStatus_whenApiReturnsIt(boolean expectedStatus) {
        PokeApiResponseDto apiResponseDto = pokeApiResponseWithLegendaryStatus(expectedStatus);

        doReturn(ResponseEntity.ok(apiResponseDto))
                .when(restTemplate).getForEntity(anyString(), eq(PokeApiResponseDto.class));

        PokemonResponseDto result = service.getBasicInformation(randomString()).get();

        assertThat(result.isLegendary()).isEqualTo(expectedStatus);
    }

    @Test
    public void getBasicInformation_shouldReturnHabitat_whenApiReturnsIt() {
        String expectedHabitat = randomString();
        PokeApiResponseDto apiResponseDto = pokeApiResponseWithHabitat(expectedHabitat);

        doReturn(ResponseEntity.ok(apiResponseDto))
                .when(restTemplate).getForEntity(anyString(), eq(PokeApiResponseDto.class));

        PokemonResponseDto result = service.getBasicInformation(randomString()).get();

        assertThat(result.getHabitat()).isEqualTo(expectedHabitat);
    }

    @Test
    public void getBasicInformation_shouldReturnEnglishName_whenApiReturnsIt() {
        String expectedName = randomString();
        PokeApiResponseDto apiResponseDto = pokeApiResponseWithNames(
                FixtureProvider.randomPokeApiName(),
                FixtureProvider.pokeApiNameWithNameAndLanguage(expectedName, LANGUAGE_ENGLISH),
                FixtureProvider.randomPokeApiName(),
                FixtureProvider.randomPokeApiName());

        doReturn(ResponseEntity.ok(apiResponseDto))
                .when(restTemplate).getForEntity(anyString(), eq(PokeApiResponseDto.class));

        PokemonResponseDto result = service.getBasicInformation(randomString()).get();

        assertThat(result.getName()).isEqualTo(expectedName);
    }

    @Test
    public void getBasicInformation_shouldReturnEnglishDescription_whenApiReturnsIt() {
        String expectedDescription = randomString();
        PokeApiResponseDto apiResponseDto = pokeApiResponseWithFlavourTextEntries(
                FixtureProvider.randomFlavourTextEntry(),
                FixtureProvider.randomFlavourTextEntry(),
                FixtureProvider.randomFlavourTextEntryWithLanguage(expectedDescription, LANGUAGE_ENGLISH),
                FixtureProvider.randomFlavourTextEntry());

        doReturn(ResponseEntity.ok(apiResponseDto))
                .when(restTemplate).getForEntity(anyString(), eq(PokeApiResponseDto.class));

        PokemonResponseDto result = service.getBasicInformation(randomString()).get();

        assertThat(result.getDescription()).isEqualTo(expectedDescription);
    }

    @Test
    public void getBasicInformation_shouldCallTheRightEndpoint() {
        String pokemonName = randomString();

        doReturn(ResponseEntity.ok(randomPokeApiResponseDto()))
                .when(restTemplate).getForEntity(anyString(), eq(PokeApiResponseDto.class));

        service.getBasicInformation(pokemonName);

        verify(restTemplate).getForEntity(eq(POKE_API_V2_SPECIES_ENDPOINT +pokemonName), eq(PokeApiResponseDto.class));
    }

    @Test
    public void getBasicInformation_shouldReturnEmpty_whenApiDoesntReturnPokemon() {
        String pokemonName = randomString();

        doReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build())
                .when(restTemplate).getForEntity(anyString(), eq(PokeApiResponseDto.class));

        Optional<PokemonResponseDto> responseDto = service.getBasicInformation(pokemonName);

        assertThat(responseDto).isNotNull().isEmpty();
    }
}
