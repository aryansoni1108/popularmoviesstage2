package com.aryan.movies;

/**
 * Created by Aryan Soni on 6/11/2018.
 */

public class MovieReviews {
    private String author;
    private String content;
    private String id;

    public MovieReviews(String author, String content, String id) {
        this.author = author;
        this.content = content;
        this.id = id;
    }


    public String getid() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
}
