package com.codingnagger.pokechallenge.model.pokeapi;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Data
@Jacksonized
public class PokeApiFlavourTextEntryDto {
    String flavorText;
    PokeApiReferencedNameDto language;
    PokeApiReferencedNameDto version;
}
