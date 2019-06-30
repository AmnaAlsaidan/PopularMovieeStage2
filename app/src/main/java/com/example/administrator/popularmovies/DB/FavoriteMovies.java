package com.example.administrator.popularmovies.DB;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "FavMovies")
public class FavoriteMovies {
    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
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

   public FavoriteMovies(int movieID, String movieName, String moviePoster, String plotSynopsis, String userRating, String releaseDate) {
        this.movieID = movieID;
        this.movieName = movieName;
        this.moviePoster = moviePoster;
        this.plotSynopsis = plotSynopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "movieId") private int movieID;
    @ColumnInfo(name = "movieName") private String movieName;
    @ColumnInfo(name = "moviePoster") private String moviePoster;
    @ColumnInfo(name = "plotSynopsis")private String plotSynopsis;
    @ColumnInfo(name = "userRating")private String userRating;
    @ColumnInfo(name = "releaseDate")private String releaseDate;
    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

/*    public  FavoriteMovies(int movieID,String movieName,String moviePoster)
    {
        this.movieID=movieID;
        this.movieName=movieName;
        this.moviePoster=moviePoster;
    }*/


}
