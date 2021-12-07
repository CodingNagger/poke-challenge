package com.codingnagger.pokechallenge.model.pokeapi;

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
    boolean isLegendary;
    List<PokeApiFlavourTextEntryDto> flavourTextEntries;
}
