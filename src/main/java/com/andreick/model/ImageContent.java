package com.andreick.model;

import java.util.Objects;
import java.util.StringJoiner;

public class ImageContent {

    private final String title;
    private final String url;
    private final String text;

    public ImageContent(String title, String url, String text) {
        this.title = title;
        this.url = url;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageContent imageContent = (ImageContent) o;
        return Objects.equals(title, imageContent.title)
                && Objects.equals(url, imageContent.url)
                && Objects.equals(text, imageContent.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url, text);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ImageContent.class.getSimpleName() + "[", "]")
                .add("title='" + title + "'")
                .add("url='" + url + "'")
                .add("text='" + text + "'")
                .toString();
    }
}
