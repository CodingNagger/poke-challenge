package com.codingnagger.pokechallenge.services.translation;

import com.codingnagger.pokechallenge.model.PokemonDto;

public interface TranslationStrategy {
    public PokemonDto translate(PokemonDto pokemonDto);
}
