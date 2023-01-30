package com.andreick;

import com.andreick.api.imdb.ImdbApi;
import com.andreick.api.nasa.NasaApi;
import com.andreick.content.ImageContentExtractor;
import com.andreick.content.ImdbImageContentExtractor;
import com.andreick.content.NasaImageContentExtractor;
import com.andreick.model.ImageContent;
import com.andreick.util.image.ImageFileHandler;
import com.andreick.util.image.ImageTextDrawer;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {

    public static void main(String[] args) {

        var nasaApi = new NasaApi();
        String apodsInRange = nasaApi.getApodsInRange(LocalDate.now().minusDays(2));

        ImageContentExtractor extractor = new NasaImageContentExtractor();
        var apods = extractor.extract(apodsInRange);

        var imdbApi = new ImdbApi();
        String top250Movies = imdbApi.getTop250Movies();

        extractor = new ImdbImageContentExtractor();
        var movies = extractor.extract(top250Movies);
        
        var images = Stream.concat(apods.stream(), movies.stream())
                .collect(Collectors.toUnmodifiableList());

        images.stream()
                .limit(10)
                .forEach(App::saveContent);
    }

    private static void saveContent(ImageContent content) {
        InputStream imageStream;
        try {
            imageStream = new URL(content.getUrl()).openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        RenderedImage newImage = ImageTextDrawer.drawText(imageStream, content.getText());
        ImageFileHandler.saveImage(newImage, content.getTitle());
    }
}
