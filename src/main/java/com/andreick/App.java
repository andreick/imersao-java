package com.andreick;

import com.andreick.model.MovieDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Properties;

public class App {

    public static void main(String[] args) throws IOException, InterruptedException {

        var imdbProps = new Properties();
        InputStream imdbPropsStream = Resources.getResourceAsStream("imdb-api.properties");
        imdbProps.load(imdbPropsStream);

        var uri = URI.create("https://imdb-api.com/en/API/Top250Movies/" + imdbProps.getProperty("key"));

        var httpClient = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(uri).GET().build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        List<MovieDto> movies = parseMoviesJson(response.body());

        var drawer = new MovieRatingDrawer();

        int limit = Math.min(movies.size(), 10);

        for (int i = 0; i < limit; i++) {
            MovieDto movie = movies.get(i);
            var imageStream = new URL(movie.getImageUrl()).openStream();
            drawer.saveImage(imageStream, movie.getTitle(), movie.getRating());
        }
    }

    public static List<MovieDto> parseMoviesJson(String moviesJson) throws IOException {

        var objMapper = new ObjectMapper();
        objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JsonNode rootNode = objMapper.readTree(moviesJson);
        JsonNode itemsNode = rootNode.path("items");

        return objMapper.readValue(itemsNode.traverse(), new TypeReference<>() {
        });
    }
}
