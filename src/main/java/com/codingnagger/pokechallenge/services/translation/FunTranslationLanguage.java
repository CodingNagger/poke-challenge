package com.codingnagger.pokechallenge.services.translation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum FunTranslationLanguage {
    YODA("yoda"),
    SHAKESPEARE("shakespeare");

    private final String requestPathParameter;
}
