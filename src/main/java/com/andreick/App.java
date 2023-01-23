package com.andreick;

import com.andreick.model.MovieDto;
import com.andreick.util.ImageFileHandler;
import com.andreick.util.ImageTextDrawer;
import com.andreick.util.Resources;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.image.RenderedImage;
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

        var drawer = new ImageTextDrawer();
        var fileHandler = new ImageFileHandler();

        movies.stream()
                .limit(Math.min(movies.size(), 10))
                .forEach(movie -> saveMovieImage(movie, drawer, fileHandler));
    }

    private static void saveMovieImage(MovieDto movie, ImageTextDrawer drawer, ImageFileHandler fileHandler) {
        InputStream imageStream;
        try {
            imageStream = new URL(movie.getImageUrl()).openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String text = "Rating: " + movie.getRating();

        RenderedImage newImage = drawer.drawText(imageStream, text);
        fileHandler.saveImage(newImage, movie.getTitle());
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
