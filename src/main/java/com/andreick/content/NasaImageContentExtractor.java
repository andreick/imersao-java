package com.andreick.content;

import com.andreick.model.ImageContent;
import com.andreick.util.parser.NasaJsonParser;

import java.util.List;
import java.util.stream.Collectors;

public class NasaImageContentExtractor implements ImageContentExtractor {

    private final NasaJsonParser parser;

    public NasaImageContentExtractor() {
        this.parser = new NasaJsonParser();
    }

    @Override
    public List<ImageContent> extract(String json) {
        return parser.parseApods(json)
                .stream()
                .map(apod -> new ImageContent(apod.getTitle(), apod.getUrl(), apod.getTitle()))
                .collect(Collectors.toList());
    }
}
