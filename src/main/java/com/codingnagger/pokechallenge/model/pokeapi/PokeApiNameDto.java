package com.codingnagger.pokechallenge.model.pokeapi;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Jacksonized
public class PokeApiNameDto {
    String name;
    PokeApiReferencedNameDto language;
}
