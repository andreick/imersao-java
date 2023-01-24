package com.andreick.api.nasa;

import com.andreick.api.ApiClient;
import com.andreick.api.ApiProperties;

import java.net.URI;
import java.time.LocalDate;

public class NasaApi {

    private static final String APOD_URL = "https://api.nasa.gov/planetary/apod?api_key=";
    private static final String PARAM_START_DATE = "&start_date=";

    private final String apiKey;
    private final ApiClient apiClient;

    public NasaApi() {
        var apiProperties = new ApiProperties();
        this.apiKey = apiProperties.getKey("nasa");
        this.apiClient = new ApiClient();
    }

    public String getApodsInRange(LocalDate startDate) {
        var uri = URI.create(APOD_URL + apiKey + PARAM_START_DATE + startDate);
        var response = apiClient.getResponse(uri);
        return response.body();
    }
}
