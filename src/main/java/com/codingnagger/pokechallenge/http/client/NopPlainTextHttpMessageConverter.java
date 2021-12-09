package com.codingnagger.pokechallenge.http.client;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class NopPlainTextHttpMessageConverter<T> implements HttpMessageConverter<T> {
    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return MediaType.TEXT_PLAIN.isCompatibleWith(mediaType);
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Collections.singletonList(MediaType.TEXT_PLAIN);
    }

    @Override
    public T read(Class<? extends T> clazz, HttpInputMessage inputMessage) throws HttpMessageNotReadableException {
        return null;
    }

    @Override
    public void write(T t, MediaType contentType, HttpOutputMessage outputMessage) throws HttpMessageNotWritableException {

    }
}
