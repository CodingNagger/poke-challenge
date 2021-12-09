package com.codingnagger.pokechallenge.services.translation;

import com.codingnagger.pokechallenge.model.PokemonDto;

public interface TranslationStrategy {
    PokemonDto translate(PokemonDto pokemonDto);
}
