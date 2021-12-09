package com.codingnagger.pokechallenge.controllers;

import com.codingnagger.pokechallenge.model.PokemonDto;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "poke-api.uri=http://localhost:9998/api",
                "funtranslation-api.uri=http://localhost:9997"
        })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebClient
public class TranslatedInformationControllerIT {
    private final static WireMockServer POKEAPI_SERVER = new WireMockServer(options().port(9998));
    private final static WireMockServer FUNTRANSLATION_SERVER = new WireMockServer(options().port(9997));

    @Autowired
    TestRestTemplate testRestTemplate;

    @BeforeAll
    static void setUp() {
        FUNTRANSLATION_SERVER.start();
        POKEAPI_SERVER.start();
    }

    @Test
    public void getTranslatedPokemonInfo_shouldReturnInfo_fromPokeApiAndTranslationApi() {
        FUNTRANSLATION_SERVER.stubFor(post(urlEqualTo("/translate/shakespeare.json"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("translation_response.json")));

        POKEAPI_SERVER.stubFor(get(urlEqualTo("/api/v2/pokemon-species/charizard"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("charizard_response.json")));

        PokemonDto expectedInfo = PokemonDto.builder()
                .name("Charizard")
                .description("Used a non pokemon-related text to test the integration of this api,  I have.")
                .habitat("mountain")
                .isLegendary(false)
                .build();

        ResponseEntity<PokemonDto> response = testRestTemplate.getForEntity("/pokemon/translated/charizard", PokemonDto.class);

        assertThat(response).isNotNull()
                .satisfies(res -> {
                    assertThat(res.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
                    assertThat(res.getBody()).isEqualTo(expectedInfo);
                });
    }

    @Test
    public void getTranslatedPokemonInfo_shouldReturnEmptyNotFoundResponse_whenPokemonNotFoundOnPokeApi() {
        POKEAPI_SERVER.stubFor(get(urlEqualTo("/api/v2/pokemon-species/november"))
                .willReturn(aResponse().withStatus(404)
                        .withHeader(CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
                        .withBody("Not Found")));

        ResponseEntity<PokemonDto> response = testRestTemplate.getForEntity("/pokemon/translated/november", PokemonDto.class);

        assertThat(response).isNotNull()
                .satisfies(res -> {
                    assertThat(res.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
                    assertThat(res.getBody()).isNull();
                });
    }
}
