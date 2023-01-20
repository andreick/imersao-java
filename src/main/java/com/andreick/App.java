package com.andreick;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class App {

    public static void main(String[] args) throws IOException, InterruptedException {

        var imdbProps = new Properties();
        var imdbPropsStream = Resources.getResourceAsStream("imdb-api.properties");
        imdbProps.load(imdbPropsStream);

        var uri = URI.create("https://imdb-api.com/en/API/Top250Movies/" + imdbProps.getProperty("key"));

        var httpClient = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(uri).GET().build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        var movies = parseMoviesJson(response.body());

        movies.forEach(movieMap -> {
            System.out.println(movieMap.get("title"));
            System.out.println(movieMap.get("image"));
            System.out.println(movieMap.get("imDbRating"));
            System.out.println();
        });
    }

    public static List<Map<String, String>> parseMoviesJson(String moviesJson) throws IOException {

        List<Map<String, String>> movies = new ArrayList<>();

        try (JsonParser jParser = new JsonFactory().createParser(moviesJson)) {

            if (jParser.nextToken() != JsonToken.START_OBJECT) {
                throw new IllegalStateException("Token expected to be a start object");
            }

            while (jParser.nextToken() != JsonToken.END_OBJECT) {

                String field = jParser.getCurrentName();

                if ("items".equals(field)) {

                    if (jParser.nextToken() != JsonToken.START_ARRAY) {
                        throw new IllegalStateException("Token expected to be a start array");
                    }

                    while (jParser.nextToken() != JsonToken.END_ARRAY) {

                        Map<String, String> movieMap = new HashMap<>();

                        while (jParser.nextToken() != JsonToken.END_OBJECT) {

                            String movieField = jParser.getCurrentName();
                            jParser.nextToken(); // Move to value token

                            switch (movieField) {
                                case "title":
                                case "image":
                                case "imDbRating":
                                    movieMap.put(movieField, jParser.getText());
                                    break;
                            }
                        }

                        movies.add(movieMap);
                    }

                    break;
                }
            }
        }

        return movies;
    }
}
