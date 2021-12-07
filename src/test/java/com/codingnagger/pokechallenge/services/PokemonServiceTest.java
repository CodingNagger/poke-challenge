package com.codingnagger.pokechallenge.services;


import com.codingnagger.pokechallenge.model.PokemonResponseDto;
import com.codingnagger.pokechallenge.model.pokeapi.PokeApiResponseDto;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PokemonService service;

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void getBasicInformation_shouldReturnPokemonLegendaryStatus_whenApiReturnsIt(boolean expectedStatus) {
        PokeApiResponseDto apiResponseDto = PokeApiResponseDto.builder().isLegendary(expectedStatus).build();

        doReturn(ResponseEntity.ok(apiResponseDto)).when(restTemplate).getForEntity(anyString(), eq(PokeApiResponseDto.class));

        PokemonResponseDto result = service.getBasicInformation("mewtwo");

        assertThat(result.isLegendary()).isEqualTo(expectedStatus);
    }
}
