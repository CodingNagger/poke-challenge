package com.codingnagger.pokechallenge.model.pokeapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder
@Data
@Jacksonized
public class PokeApiResponseDto {
    List<PokeApiNameDto> names;
    PokeApiReferencedNameDto habitat;

    @JsonProperty("is_legendary")
    boolean isLegendary;

    @JsonProperty("flavor_text_entries")
    List<PokeApiFlavourTextEntryDto> flavourTextEntries;
}
