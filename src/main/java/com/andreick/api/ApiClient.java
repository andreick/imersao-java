package com.andreick.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {

    private final HttpClient httpClient;

    public ApiClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public String getBody(URI uri) {
        try {
            var request = HttpRequest.newBuilder(uri).GET().build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
