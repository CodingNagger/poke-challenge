package com.codingnagger.pokechallenge.model.pokeapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Data
@Jacksonized
public class PokeApiFlavourTextEntryDto {
    @JsonProperty("flavor_text")
    String flavorText;
    PokeApiReferencedNameDto language;
    PokeApiReferencedNameDto version;
}
