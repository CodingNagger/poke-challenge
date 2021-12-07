package com.codingnagger.pokechallenge.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PokemonResponseDto {
    String name;
    String description;
    String habitat;
    boolean isLegendary;
}
