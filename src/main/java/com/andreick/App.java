package com.andreick;

import com.andreick.api.imdb.ImdbApi;
import com.andreick.model.MovieDto;
import com.andreick.util.image.ImageFileHandler;
import com.andreick.util.image.ImageTextDrawer;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {

    public static void main(String[] args) {

        ImdbApi imdbApi = new ImdbApi();
        List<MovieDto> top250Movies = imdbApi.getTop250Movies();

        top250Movies.stream()
                .limit(Math.min(top250Movies.size(), 10))
                .forEach(App::saveMovieImage);
    }

    private static void saveMovieImage(MovieDto movie) {
        InputStream imageStream;
        try {
            imageStream = new URL(movie.getImageUrl()).openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String text = "Rating: " + movie.getRating();

        RenderedImage newImage = ImageTextDrawer.drawText(imageStream, text);
        ImageFileHandler.saveImage(newImage, movie.getTitle());
    }
}
