package com.codingnagger.pokechallenge.services.translation;

import com.codingnagger.pokechallenge.model.PokemonDto;
import org.springframework.stereotype.Component;

@Component
public class TranslationStrategyProvider {
    public TranslationStrategy determineStrategy(PokemonDto pokemonDto) {
        return null;
    }
}
