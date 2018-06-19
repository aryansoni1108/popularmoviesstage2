package com.aryan.movies;

/**
 * Created by Aryan Soni on 6/11/2018.
 */

public class MovieVideos {
    private String key;
    private String name;

    public MovieVideos(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getname() {
        return name;
    }

    public String getkey() {
        return key;
    }
}
