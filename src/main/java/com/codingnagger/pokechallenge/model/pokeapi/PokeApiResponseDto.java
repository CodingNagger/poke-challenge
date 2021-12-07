package com.codingnagger.pokechallenge.model.pokeapi;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Jacksonized
public class PokeApiResponseDto {
    PokeApiNameDto[] names;
    PokeApiReferencedNameDto habitat;
    boolean isLegendary;
    PokeApiFlavourTextEntryDto[] flavourTextEntries;
}

/**
 - Can use any of the flavor_text_entries entries for description, use first one matching language "en"
 - name is in names, use the first one matching "en"
 - use is_legendary for legendary status
 - use habitat.name for habitat
 */