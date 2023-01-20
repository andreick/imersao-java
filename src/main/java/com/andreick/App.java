package com.andreick;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
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

        movies.forEach(movieMap -> {
            System.out.println(movieMap.get("title"));
            System.out.println(movieMap.get("image"));
            System.out.println(movieMap.get("imDbRating"));
            System.out.println();
        });
    }

    public static List<Map<String, String>> parseMoviesJson(String moviesJson) throws IOException {

        var objMapper = new ObjectMapper();

        JsonNode rootNode = objMapper.readTree(moviesJson);
        JsonNode itemsNode = rootNode.path("items");

        return objMapper.readValue(itemsNode.traverse(), new TypeReference<>() {
        });
    }
}
