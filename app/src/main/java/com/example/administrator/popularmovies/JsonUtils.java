package com.example.administrator.popularmovies;

import android.util.Log;

import com.example.administrator.popularmovies.models.Movies;
import com.example.administrator.popularmovies.models.MoviesReviews;
import com.example.administrator.popularmovies.models.MoviesTrailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static List<Movies> parseMovieJson(String json) throws JSONException {
        JSONObject movieObject = new JSONObject(json);
        JSONArray results = movieObject.getJSONArray("results");
        List<Movies> moviesList=new ArrayList<>();

        for (int i = 0; i < results.length(); ++i) {
            moviesList.add(new Movies(Integer.parseInt(results.getJSONObject(i).getString("id")),results.getJSONObject(i).getString("title"),
                    results.getJSONObject(i).getString("poster_path"),
                    results.getJSONObject(i).getString("overview"),
                    results.getJSONObject(i).getString("vote_average"),
                    results.getJSONObject(i).getString("release_date")));
        }
        return moviesList;
    }   public static List<MoviesTrailer> parseMovieTrailerJson(String json) throws JSONException {
        JSONObject movieObject = new JSONObject(json);
        JSONArray results = movieObject.getJSONArray("results");
        List<MoviesTrailer> moviesTrailerList=new ArrayList<>();

        for (int i = 0; i < results.length(); ++i) {
            moviesTrailerList.add(new MoviesTrailer
                    (results.getJSONObject(i).getString("type"),
                            results.getJSONObject(i).getString("key"),
                            results.getJSONObject(i).getString("site")));
        }
        return moviesTrailerList;
    } public static List<MoviesReviews> parseMovieReviewsJson(String json) throws JSONException {
        JSONObject movieObject = new JSONObject(json);
        JSONArray results = movieObject.getJSONArray("results");
        List<MoviesReviews> moviesReviewsList=new ArrayList<>();

        for (int i = 0; i < results.length(); ++i) {
            moviesReviewsList.add(new MoviesReviews(results.getJSONObject(i).getString("author"),
                    results.getJSONObject(i).getString("content"),results.getJSONObject(i).getString("url")));
        }
        return moviesReviewsList;
    }
}
