package com.codingnagger.pokechallenge.services;

import com.codingnagger.pokechallenge.model.PokemonResponseDto;
import com.codingnagger.pokechallenge.model.pokeapi.PokeApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class PokemonService {
    private final RestTemplate restTemplate;

    PokemonResponseDto getBasicInformation(String pokemonName) {
        ResponseEntity<PokeApiResponseDto> response = restTemplate.getForEntity("/v2/pokemon-species/mewtwo",
                PokeApiResponseDto.class);

        return mapResponse(response.getBody());
    }

    private PokemonResponseDto mapResponse(PokeApiResponseDto pokeApiResponseDto) {
        return PokemonResponseDto.builder()
                .isLegendary(pokeApiResponseDto.isLegendary())
                .build();
    }
}
