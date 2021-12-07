package com.codingnagger.pokechallenge.services;

import com.codingnagger.pokechallenge.model.PokemonResponseDto;
import com.codingnagger.pokechallenge.model.pokeapi.PokeApiFlavourTextEntryDto;
import com.codingnagger.pokechallenge.model.pokeapi.PokeApiNameDto;
import com.codingnagger.pokechallenge.model.pokeapi.PokeApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class PokemonService {
    public static final String LANGUAGE_ENGLISH = "en";

    private final RestTemplate restTemplate;

    Optional<PokemonResponseDto> getBasicInformation(String pokemonName) {
        ResponseEntity<PokeApiResponseDto> response = restTemplate.getForEntity("/v2/pokemon-species/"+pokemonName,
                PokeApiResponseDto.class);

        if (Objects.equals(HttpStatus.OK, response.getStatusCode())) {
            return Optional.of(mapResponse(response.getBody()));
        }

        return Optional.empty();
    }

    private PokemonResponseDto mapResponse(PokeApiResponseDto pokeApiResponseDto) {
        return PokemonResponseDto.builder()
                .name(extractEnglishName(pokeApiResponseDto))
                .description(extractDescription(pokeApiResponseDto))
                .isLegendary(pokeApiResponseDto.isLegendary())
                .habitat(pokeApiResponseDto.getHabitat().getName())
                .build();
    }

    private String extractDescription(PokeApiResponseDto pokeApiResponseDto) {
        return pokeApiResponseDto.getFlavourTextEntries().stream()
                .filter(n -> LANGUAGE_ENGLISH.equalsIgnoreCase(n.getLanguage().getName()))
                .map(PokeApiFlavourTextEntryDto::getFlavorText)
                .findFirst()
                .get();
    }

    private String extractEnglishName(PokeApiResponseDto pokeApiResponseDto) {
        return pokeApiResponseDto.getNames().stream()
                .filter(n -> LANGUAGE_ENGLISH.equalsIgnoreCase(n.getLanguage().getName()))
                .map(PokeApiNameDto::getName)
                .findFirst()
                .get();
    }
}
