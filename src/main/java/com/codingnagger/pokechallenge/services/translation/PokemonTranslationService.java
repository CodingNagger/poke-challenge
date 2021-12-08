package com.codingnagger.pokechallenge.services.translation;

import com.codingnagger.pokechallenge.model.PokemonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PokemonTranslationService {
    public static final String HABITAT_CAVE = "cave";
    private final FunTranslationService funTranslationService;

    @NonNull
    public PokemonDto translate(PokemonDto pokemonDto) {
        Optional<String> translatedDescription =
                funTranslationService.translate(determineLanguage(pokemonDto), pokemonDto.getDescription());

        if (translatedDescription.isEmpty()) {
            return pokemonDto;
        }

        return PokemonDto.builder()
                .name(pokemonDto.getName())
                .habitat(pokemonDto.getHabitat())
                .isLegendary(pokemonDto.isLegendary())
                .description(translatedDescription.get())
                .build();
    }

    private FunTranslationLanguage determineLanguage(PokemonDto pokemonDto) {
        if (isPokemonYodaFriendly(pokemonDto)) {
            return FunTranslationLanguage.YODA;
        }

        return FunTranslationLanguage.SHAKESPEARE;
    }

    private boolean isPokemonYodaFriendly(PokemonDto pokemonDto) {
        return pokemonDto.isLegendary() || HABITAT_CAVE.equalsIgnoreCase(pokemonDto.getHabitat());
    }
}
