package com.andreick;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class App {

    public static void main(String[] args) throws IOException, InterruptedException {

        var imdbProps = new Properties();
        var imdbPropsStream = App.class.getClassLoader().getResourceAsStream("imdb-api.properties");
        imdbProps.load(imdbPropsStream);

        var uri = URI.create("https://imdb-api.com/en/API/Top250Movies/" + imdbProps.getProperty("key"));

        var httpClient = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(uri).GET().build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        var responseJson = new JSONObject(response.body());
        var moviesJsonArray = responseJson.getJSONArray("items");

        moviesJsonArray.forEach(movieObj -> {
            var movieJson = (JSONObject) movieObj;
            System.out.println(movieJson.get("title"));
            System.out.println(movieJson.get("image"));
            System.out.println(movieJson.get("imDbRating"));
            System.out.println();
        });
    }
}
