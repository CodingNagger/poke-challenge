package com.codingnagger.pokechallenge.model.translation;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Data
@Jacksonized
public class FunTranslationRequestDto {
    String text;
}
