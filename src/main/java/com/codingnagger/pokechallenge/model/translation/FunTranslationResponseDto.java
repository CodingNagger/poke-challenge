package com.codingnagger.pokechallenge.model.translation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Data
@Jacksonized
public class FunTranslationResponseDto {
    FunTranslationContentsDto contents;
}
