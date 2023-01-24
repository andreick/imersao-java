package com.andreick.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.StringJoiner;

public class ApodDto {

    private final String title;
    private final String url;

    @JsonCreator
    public ApodDto(@JsonProperty("title") String title,
                   @JsonProperty("url") String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApodDto apodDto = (ApodDto) o;
        return Objects.equals(title, apodDto.title)
                && Objects.equals(url, apodDto.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url);
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", ApodDto.class.getSimpleName() + "[", "]")
                .add("title='" + title + "'")
                .add("url='" + url + "'")
                .toString();
    }
}
