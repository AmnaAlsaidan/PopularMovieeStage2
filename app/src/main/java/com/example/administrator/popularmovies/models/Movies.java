package com.example.administrator.popularmovies.models;

import java.io.Serializable;

/*original title
        movie poster image thumbnail
        A plot synopsis (called overview in the api)
        user rating (called vote_average in the api)
            Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        release d*/
public class Movies implements Serializable {
/*    public Movies(String movieId, String originalTitle, String moviePoster, String plotSynopsis, String userRating, String releaseDate) {
        this.originalTitle = originalTitle;
        this.moviePoster = moviePoster;
        this.plotSynopsis = plotSynopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.movieID = movieId;
    }*/
    public Movies(int movieId, String originalTitle, String moviePoster, String plotSynopsis, String userRating, String releaseDate) {
        this.originalTitle = originalTitle;
        this.moviePoster = moviePoster;
        this.plotSynopsis = plotSynopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.id= movieId;
    }

    public Movies(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public Movies() {
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    String originalTitle;
    String moviePoster;
    String plotSynopsis;
    String userRating;
    String releaseDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;
    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    String movieID;


}
