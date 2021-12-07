package com.codingnagger.pokechallenge.controllers;

import com.codingnagger.pokechallenge.model.PokemonDto;
import com.codingnagger.pokechallenge.services.PokemonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BasicInformationController {
    private final PokemonService pokemonService;

    @GetMapping("/pokemon/{pokemonName}")
    public ResponseEntity<PokemonDto> getPokemonInfo(@PathVariable("pokemonName") String pokemonName) {
        Optional<PokemonDto> responseDto = pokemonService.getBasicInformation(pokemonName);

        if (responseDto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(responseDto.get());
    }
}
