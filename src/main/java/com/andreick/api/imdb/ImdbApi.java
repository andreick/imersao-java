package com.andreick.api.imdb;

import com.andreick.api.ApiClient;
import com.andreick.model.MovieDto;
import com.andreick.util.parser.ImdbJsonParser;

import java.net.URI;
import java.util.List;

public class ImdbApi {

    private static final String TOP_250_MOVIES_URL = "https://imdb-api.com/en/API/Top250Movies/";

    private final ImdbProperties properties;
    private final ApiClient apiClient;
    private final ImdbJsonParser jsonParser;

    public ImdbApi() {
        this.properties = new ImdbProperties();
        this.apiClient = new ApiClient();
        this.jsonParser = new ImdbJsonParser();
    }

    public List<MovieDto> getTop250Movies() {
        var uri = URI.create(TOP_250_MOVIES_URL + properties.getKey());
        String json = apiClient.getBody(uri);
        return jsonParser.parseMovies(json);
    }
}
