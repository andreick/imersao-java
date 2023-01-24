package com.andreick.util.parser;

import com.andreick.model.ApodDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class NasaJsonParser {

    private final ObjectMapper objectMapper;

    public NasaJsonParser() {
        this.objectMapper = new ObjectMapper();
    }

    public List<ApodDto> parseApods(String json) {

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
