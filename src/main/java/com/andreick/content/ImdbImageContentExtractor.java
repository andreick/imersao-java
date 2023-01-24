package com.andreick.content;

import com.andreick.model.ImageContent;
import com.andreick.util.parser.ImdbJsonParser;

import java.util.List;
import java.util.stream.Collectors;

public class ImdbImageContentExtractor implements ImageContentExtractor {

    private final ImdbJsonParser parser;

    public ImdbImageContentExtractor() {
        this.parser = new ImdbJsonParser();
    }

    @Override
    public List<ImageContent> extract(String json) {
        return parser.parseMovies(json)
                .stream()
                .map(movie -> {
                    String url = movie.getImageUrl().replaceAll("\\._.*_\\.", ".");
                    String text = "IMDb rating: " + movie.getRating();
                    return new ImageContent(movie.getTitle(), url, text);
                })
                .collect(Collectors.toList());
    }
}
