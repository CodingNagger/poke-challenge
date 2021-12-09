package com.codingnagger.pokechallenge.http.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class DefaultRestTemplateErrorHandlerTest {
    private final DefaultRestTemplateErrorHandler errorHandler = new DefaultRestTemplateErrorHandler();

    @Mock
    private ClientHttpResponse response;

    @Test
    public void hasError_shouldReturnFalse_whenResponseStatusIsOk() throws IOException {
        doReturn(HttpStatus.OK).when(response).getStatusCode();

        boolean result = errorHandler.hasError(response);

        assertThat(result).isFalse();
    }

    @Test
    public void hasError_shouldReturnFalse_whenResponseStatusIsNotFound() throws IOException {
        doReturn(HttpStatus.NOT_FOUND).when(response).getStatusCode();

        boolean result = errorHandler.hasError(response);

        assertThat(result).isTrue();
    }

    @Test
    public void hasError_shouldNeverInteractWithResponse() {
        errorHandler.handleError(response);

        verifyNoInteractions(response);
    }
}
