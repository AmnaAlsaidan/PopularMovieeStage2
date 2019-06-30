package com.example.administrator.popularmovies.models;

public class MoviesTrailer {
    public MoviesTrailer(String type, String key, String site) {
        this.type = type;
        this.key = key;
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    String type;
    String key;
    String site;

}
