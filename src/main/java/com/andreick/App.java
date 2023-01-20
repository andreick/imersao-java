package com.andreick;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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

        var drawer = new MovieRatingDrawer();

        int limit = Math.min(movies.size(), 10);

        for (int i = 0; i < limit; i++) {
            var movieMap = movies.get(i);

            String image = movieMap.get("image").replace("_V1_UX128_CR0,12,128,176_AL_.", "");
            String title = movieMap.get("title");
            String rating = movieMap.get("imDbRating");

            var imageStream = new URL(image).openStream();
            drawer.saveImage(imageStream, title, rating);
        }
    }

    public static List<Map<String, String>> parseMoviesJson(String moviesJson) throws IOException {

        var objMapper = new ObjectMapper();

        JsonNode rootNode = objMapper.readTree(moviesJson);
        JsonNode itemsNode = rootNode.path("items");

        return objMapper.readValue(itemsNode.traverse(), new TypeReference<>() {
        });
    }
}
