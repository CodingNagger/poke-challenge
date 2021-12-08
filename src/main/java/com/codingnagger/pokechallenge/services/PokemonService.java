package com.codingnagger.pokechallenge.services;

import com.codingnagger.pokechallenge.model.PokemonDto;
import com.codingnagger.pokechallenge.model.pokeapi.PokeApiFlavourTextEntryDto;
import com.codingnagger.pokechallenge.model.pokeapi.PokeApiNameDto;
import com.codingnagger.pokechallenge.model.pokeapi.PokeApiResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

@Service
public class PokemonService {
    public static final String LANGUAGE_ENGLISH = "en";
    public static final String V2_POKEMON_SPECIES_ENDPOINT = "/v2/pokemon-species/";

    private final RestTemplate restTemplate;

    public PokemonService(@Qualifier("pokeApiRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<PokemonDto> getBasicInformation(String pokemonName) {
        ResponseEntity<PokeApiResponseDto> response = restTemplate.getForEntity(V2_POKEMON_SPECIES_ENDPOINT + pokemonName,
                PokeApiResponseDto.class);

        if (Objects.equals(HttpStatus.OK, response.getStatusCode())) {
            return Optional.of(mapResponse(response.getBody()));
        }

        return Optional.empty();
    }

    private PokemonDto mapResponse(PokeApiResponseDto pokeApiResponseDto) {
        return PokemonDto.builder()
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
