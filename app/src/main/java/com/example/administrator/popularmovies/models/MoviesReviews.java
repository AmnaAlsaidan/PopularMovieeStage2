package com.example.administrator.popularmovies.models;

public class MoviesReviews {
    public MoviesReviews(String author, String content, String reviewURL) {
        this.author = author;
        this.content = content;
        this.reviewURL=reviewURL;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    String author;
    String content;

    public String getReviewURL() {
        return reviewURL;
    }

    public void setReviewURL(String reviewURL) {
        this.reviewURL = reviewURL;
    }

    String reviewURL;

}
