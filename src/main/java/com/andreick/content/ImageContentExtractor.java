package com.andreick.content;

import com.andreick.model.ImageContent;

import java.util.List;

public interface ImageContentExtractor {

    List<ImageContent> extract(String json);
}
