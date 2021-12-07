package com.codingnagger.pokechallenge.services.translation;

import com.codingnagger.pokechallenge.model.PokemonDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TranslationService {
    private final TranslationStrategyProvider translationStrategyProvider;

    public PokemonDto translate(PokemonDto pokemonDto) {
        return translationStrategyProvider
                .determineStrategy(pokemonDto)
                .translate(pokemonDto);
    }
}
