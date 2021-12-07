package com.codingnagger.pokechallenge.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PokemonDto {
    String name;
    String description;
    String habitat;
    boolean isLegendary;
}
