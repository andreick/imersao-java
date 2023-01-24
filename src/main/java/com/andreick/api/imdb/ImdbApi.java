package com.andreick.api.imdb;

import com.andreick.api.ApiClient;
import com.andreick.api.ApiProperties;

import java.net.URI;

public class ImdbApi {

    private static final String TOP_250_MOVIES_URL = "https://imdb-api.com/en/API/Top250Movies/";

    private final String apiKey;
    private final ApiClient apiClient;

    public ImdbApi() {
        var apiProperties = new ApiProperties();
        this.apiKey = apiProperties.getKey("imdb");
        this.apiClient = new ApiClient();
    }

    public String getTop250Movies() {
        var uri = URI.create(TOP_250_MOVIES_URL + apiKey);
        var response = apiClient.getResponse(uri);
        return response.body();
    }
}
