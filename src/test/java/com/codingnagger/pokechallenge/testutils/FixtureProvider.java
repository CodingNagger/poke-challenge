package com.codingnagger.pokechallenge.testutils;

import com.codingnagger.pokechallenge.model.PokemonDto;
import com.codingnagger.pokechallenge.model.pokeapi.PokeApiFlavourTextEntryDto;
import com.codingnagger.pokechallenge.model.pokeapi.PokeApiNameDto;
import com.codingnagger.pokechallenge.model.pokeapi.PokeApiReferencedNameDto;
import com.codingnagger.pokechallenge.model.pokeapi.PokeApiResponseDto;
import com.codingnagger.pokechallenge.services.translation.FunTranslationLanguage;
import com.flextrade.jfixture.JFixture;

import java.util.Arrays;

import static java.util.Collections.singletonList;

public class FixtureProvider {
    public static final String LANGUAGE_EN = "en";
    private final static JFixture FIXTURE = new JFixture();

    public static String randomString() {
        return FIXTURE.create(String.class);
    }

    public static PokeApiResponseDto pokeApiResponseWithLegendaryStatus(boolean isLegendary) {
        PokeApiResponseDto responseDto = randomPokeApiResponse();
        responseDto.setLegendary(isLegendary);
        return responseDto;
    }

    public static PokeApiResponseDto randomPokeApiResponse() {
        return PokeApiResponseDto.builder()
                .names(singletonList(pokeApiNameWithNameAndLanguage(randomString(), LANGUAGE_EN)))
                .flavourTextEntries(singletonList(randomFlavourTextEntryWithLanguage(randomString(), LANGUAGE_EN)))
                .habitat(FIXTURE.create(PokeApiReferencedNameDto.class))
                .isLegendary(FIXTURE.create(Boolean.class))
                .build();
    }

    public static PokeApiResponseDto pokeApiResponseWithHabitat(String habitat) {
        PokeApiResponseDto responseDto = randomPokeApiResponse();
        responseDto.setHabitat(PokeApiReferencedNameDto.builder().name(habitat).build());
        return responseDto;
    }

    public static PokeApiResponseDto pokeApiResponseWithNames(PokeApiNameDto... names) {
        PokeApiResponseDto responseDto = randomPokeApiResponse();
        responseDto.setNames(Arrays.asList(names));
        return responseDto;
    }

    public static PokeApiResponseDto pokeApiResponseWithFlavourTextEntries(PokeApiFlavourTextEntryDto... flavourTextEntries) {
        PokeApiResponseDto responseDto = randomPokeApiResponse();
        responseDto.setFlavourTextEntries(Arrays.asList(flavourTextEntries));
        return responseDto;
    }

    public static PokeApiNameDto randomPokeApiName() {
        return FIXTURE.create(PokeApiNameDto.class);
    }

    public static PokeApiNameDto pokeApiNameWithNameAndLanguage(String name, String language) {
        return PokeApiNameDto.builder()
                .name(name)
                .language(getPokeApiReferencedName(language))
                .build();
    }

    private static PokeApiReferencedNameDto getPokeApiReferencedName(String language) {
        return PokeApiReferencedNameDto.builder().name(language).build();
    }

    public static PokeApiFlavourTextEntryDto randomFlavourTextEntry() {
        return FIXTURE.create(PokeApiFlavourTextEntryDto.class);
    }

    public static PokeApiFlavourTextEntryDto randomFlavourTextEntryWithLanguage(String textEntry, String language) {
        PokeApiFlavourTextEntryDto flavourTextEntryDto = randomFlavourTextEntry();
        flavourTextEntryDto.setFlavorText(textEntry);
        flavourTextEntryDto.getLanguage().setName(language);
        return flavourTextEntryDto;
    }

    public static PokemonDto randomPokemon() {
        return FIXTURE.create(PokemonDto.class);
    }

    public static FunTranslationLanguage randomFunTranslationLanguage() {
        return FIXTURE.create(FunTranslationLanguage.class);
    }
}
