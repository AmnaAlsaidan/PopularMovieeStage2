package com.example.administrator.popularmovies;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.popularmovies.DB.FavoriteMovies;
import com.example.administrator.popularmovies.DB.FavoriteMoviesDB;
import com.example.administrator.popularmovies.models.Movies;
import com.example.administrator.popularmovies.models.MoviesTrailer;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class MovieDetails extends AppCompatActivity implements LoaderManager.LoaderCallbacks, View.OnClickListener {
    private final static String IMAGE_PATH = "http://image.tmdb.org/t/p/w185//";
    List<MoviesTrailer> moviesTrailerList;
    ImageView movieFav, movieNotFav;
    String MovieDB_Videos;
    private RecyclerView recyclerView;
    int movieId;
    String releaseDateString, userRatingString, overViewString;
    FavoriteMovies favoriteMovies;
    String moviePosterFav;
    String moviePosterURLString, moviePosterURLString1;
    private FavoriteMoviesDB favoriteMoviesDB;
    String movieTitleString;
    Boolean isFav = false;
    MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    List<Movies> moviesList;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.movie_details);
        TextView movieTitle = findViewById(R.id.original_Title);
        TextView userRating = findViewById(R.id.user_rating);
        TextView releaseDate = findViewById(R.id.release_date);
        TextView overView = findViewById(R.id.synopsis);
        ImageView moviePoster = findViewById(R.id.movie_poster);
        recyclerView = findViewById(R.id.recyclerViewTrailers);
        movieFav = findViewById(R.id.isFav);
        ClearMovieItemList();
        moviesList = (List<Movies>) getIntent().getExtras().getSerializable("MoviesList");


        int position = getIntent().getExtras().getInt("position");
        assert moviesList != null;
        Log.i("moviesList", "moviesList" + moviesList.get(position).getMovieID());

        movieTitleString = moviesList.get(position).getOriginalTitle();
        overViewString = moviesList.get(position).getPlotSynopsis();
        userRatingString = moviesList.get(position).getUserRating();
        releaseDateString = moviesList.get(position).getReleaseDate();
        moviePosterURLString = IMAGE_PATH + moviesList.get(position).getMoviePoster();
        moviePosterURLString1 = moviesList.get(position).getMoviePoster();
        movieId = moviesList.get(position).getId();
        getSupportLoaderManager().initLoader(1, null, (LoaderManager.LoaderCallbacks<List<MoviesTrailer>>) this).forceLoad();
        MovieDB_Videos = "https://api.themoviedb.org/3/movie/" + movieId + "/videos?" + MainActivity.API_KEY;

        movieTitle.setText(movieTitleString);
        userRating.setText(userRatingString);
        overView.setText(overViewString);
        releaseDate.setText(releaseDateString);
        Picasso.get().load(moviePosterURLString)
                .error(R.drawable.not_avilable)
                .placeholder(R.drawable.placeholder)
                .fit()
                .into(moviePoster);
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MoviesReviewsLoaderFragment moviesReviewsLoaderFragment = new MoviesReviewsLoaderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("movieId", movieId);
        moviesReviewsLoaderFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.reviewsFragmentContainer, moviesReviewsLoaderFragment);
        fragmentTransaction.commit();


        //-------------------------------------

        favoriteMoviesDB = favoriteMoviesDB.getInstance(getApplicationContext());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                FavoriteMovies favMovies = favoriteMoviesDB.favoriteMoviesDAO().loadMovieById(movieId);
                Log.i("FavoriteMovies", "FavoriteMovies= " + favMovies);
                moviesStatus((favMovies != null) ? true : false);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("onResume", "onResume");
    }

    @Override
    public Loader<List<MoviesTrailer>> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<List<MoviesTrailer>>(MovieDetails.this.getApplicationContext()) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
            }

            @Override
            public List<MoviesTrailer> loadInBackground() {
                try {
                    // https://api.themoviedb.org/3/movie/popular?api_key=ce4bc4732cb010a2aeedb4eaf55e9557
                    String json = NetworkUtils.getResponseFromHttpUrl(MovieDB_Videos);
                    moviesTrailerList = JsonUtils.parseMovieTrailerJson(json);
                    // moviesReviewsLoader = new MoviesReviewsLoader(MovieDB_Reviews, movieId);
                    Log.i("moviesTrailerList", "SIZE= " + moviesTrailerList.size());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return moviesTrailerList;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader loader, Object o) {
     /*   if (moviesTrailerList == null) {
            Toast.makeText(this, "There are no trailer for this movie", Toast.LENGTH_LONG).show();
        } else {*/
        favoriteMovies = new FavoriteMovies(movieId, movieTitleString, moviePosterURLString1, overViewString, userRatingString, releaseDateString);

        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(moviesTrailerList, this);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setAdapter(movieRecyclerViewAdapter);
        recyclerView.setLayoutManager(layoutManager);
        movieFav.setOnClickListener(this);

/*
        }
*/
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    public void moviesStatus(boolean b) {
        if (b) {
            isFav = true;
            //movieNotFav.setVisibility(View.INVISIBLE);
            // movieFav.setVisibility(View.VISIBLE);
            movieFav.setImageResource(R.drawable.favorite);

        } else {
            isFav = false;
            // movieNotFav.setVisibility(View.VISIBLE);
            movieFav.setImageResource(R.drawable.heart);

            // movieFav.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onClick(final View view) {
        //  Movies movies=new Movies(movieId,movieTitleString)

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!isFav) {
                    favoriteMoviesDB.favoriteMoviesDAO().insertMovie(favoriteMovies);

                } else {
                    favoriteMoviesDB.favoriteMoviesDAO().deleteMovie(favoriteMovies);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        moviesStatus(!isFav);
                    }
                });

            }
        });


    }

    private void ClearMovieItemList() {
        if (moviesList != null) {
            moviesList.clear();
        } else {
            moviesList = new ArrayList<Movies>();
        }
    }

}
