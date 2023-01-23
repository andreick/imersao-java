package com.andreick.util.parser;

import com.andreick.model.MovieDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class ImdbJsonParser {

    private final ObjectMapper objectMapper;

    public ImdbJsonParser() {
        this.objectMapper = new ObjectMapper();
    }

    public List<MovieDto> parseMovies(String moviesJson) {

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            JsonNode rootNode = objectMapper.readTree(moviesJson);
            JsonNode itemsNode = rootNode.path("items");

            return objectMapper.readValue(itemsNode.traverse(), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
