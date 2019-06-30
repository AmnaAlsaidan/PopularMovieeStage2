package com.example.administrator.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.administrator.popularmovies.DB.FavoriteMovies;
import com.example.administrator.popularmovies.models.Movies;

import org.json.JSONException;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

final class InternetConnection {

    /**
     * CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT
     */
    public static boolean checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) { // connected to the internet
            Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();

            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        }
        return false;
    }
}

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    int numberOfColumns;
    String response;
    ProgressBar progressBar;
    final static String API_KEY = "api_key=ce4bc4732cb010a2aeedb4eaf55e9557";
    static String MovieDB_URL = "";
    final static String MovieDB_POPULAR_URL = "https://api.themoviedb.org/3/movie/popular?" + API_KEY;
    final static String MovieDB_TOP_RATED_URL = "https://api.themoviedb.org/3/movie/top_rated?" + API_KEY;
    // https://api.themoviedb.org/3/movie/popular?api_key=ce4bc4732cb010a2aeedb4eaf55e9557
    private String jsonMostPopular, jsonHighestRater;
    private List<Movies> moviesList;
    private List<Movies> trailersList;
    private Movies movies = new Movies();
    private List<FavoriteMovies> favMoviesList;
    MovieRecyclerViewAdapter movieRecyclerViewAdapter;

    private void loadMovieURL(String movieURL) {
        MovieDB_URL = movieURL;
        new MovieDBTask().execute(MovieDB_URL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        numberOfColumns = 2;
        MovieDB_URL = "https://api.themoviedb.org/3/movie/popular?" + API_KEY;
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar.setVisibility(View.VISIBLE);
        favMoviesList = new ArrayList<FavoriteMovies>();

        if (InternetConnection.checkConnection(this)) {
            //ClearMovieItemList();
            loadMovieURL(MovieDB_POPULAR_URL);
            setupViewModel();
        } else {
            Toast.makeText(this, "Please Check your internet connection ", Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        ActionBar actionBar = getSupportActionBar();
        if (InternetConnection.checkConnection(this)) {
            if (moviesList != null) {
                //noinspection SimplifiableIfStatement
                if (id == R.id.highest_rated) {
                    ClearMovieItemList();
                    loadMovieURL(MovieDB_TOP_RATED_URL);
                    actionBar.setTitle(R.string.highest_rated);
                    return true;
                }
                if (id == R.id.most_popular) {
                    ClearMovieItemList();
                    loadMovieURL(MovieDB_POPULAR_URL);
                    actionBar.setTitle(R.string.most_popular);

                    return true;
                }
                if (id == R.id.My_fav) {
                  //  ClearMovieItemList();
                    loadFavMovies();
                    actionBar.setTitle(R.string.fav);
                    return true;

                }
            }
        }else{
            Toast.makeText(this, "Please Check your internet connection ", Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);
    }

    private void loadFavMovies() {
        ClearMovieItemList();
        if (favMoviesList != null) {
            for (int i = 0; i < favMoviesList.size(); i++) {
                //FavoriteMovies movie = new FavoriteMovies(favMoviesList.get(i).getMovieID(), favMoviesList.get(i).getMovieName(), favMoviesList.get(i).getMoviePoster());
                Movies movie = new Movies(
                        favMoviesList.get(i).getMovieID(),
                        favMoviesList.get(i).getMovieName(),
                        favMoviesList.get(i).getMoviePoster(),
                        favMoviesList.get(i).getPlotSynopsis(),
                        favMoviesList.get(i).getUserRating(),
                        favMoviesList.get(i).getReleaseDate());
                moviesList.add(movie);

                //            favMoviesList.add(mov);
            }

            movieRecyclerViewAdapter.setFavMovies(moviesList);
            //  movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(moviesList, this, "MainActivity");
            //  recyclerView.setAdapter(movieRecyclerViewAdapter);
        }

    }

    private void setupViewModel() {
        // ClearMovieItemList();
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getFavoriteMovies().observe(this, new Observer<List<FavoriteMovies>>() {
            @Override
            public void onChanged(List<FavoriteMovies> favoriteMovies) {

                if (favoriteMovies.size() >= 0) {
                    favMoviesList.clear();
                    favMoviesList = favoriteMovies;

                }
               ClearMovieItemList();
                 // loadFavMovies();
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

    public class MovieDBTask extends AsyncTask<String, Void, List<Movies>> {

 /*       @Override
        protected List<Movies> doInBackground(Void... voids) {

            try {
                jsonMostPopular = NetworkUtils.getResponseFromHttpUrl(MovieDB_URL);
                moviesListMostPopular = JsonUtils.parseMovieJson(jsonMostPopular);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressBar.setVisibility(View.VISIBLE);

            return moviesListMostPopular;
        }
*/

        @Override
        protected List<Movies> doInBackground(String... params) {
            String url = params[0];
            Log.i("doInBackground", "" + url);
            try {
                jsonMostPopular = NetworkUtils.getResponseFromHttpUrl(url);
                moviesList = JsonUtils.parseMovieJson(jsonMostPopular);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return moviesList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Movies> movies) {
            progressBar.setVisibility(View.INVISIBLE);

            super.onPostExecute(movies);
            populateUI();
        }

    }

    public void populateUI() {
        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(moviesList, this, "MainActivity");
        recyclerView.setAdapter(movieRecyclerViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
    }

/*    private void makeSearchQuery(String url) {

        // Create a bundle called queryBundle
        Bundle queryBundle = new Bundle();
        // Use putString with OPERATION_QUERY_URL_EXTRA as the key and the String value of the URL as the value
        queryBundle.putString(OPERATION_QUERY_URL_EXTRA,url);
        // Call getSupportLoaderManager and store it in a LoaderManager variable
        LoaderManager loaderManager = getSupportLoaderManager();
        // Get our Loader by calling getLoader and passing the ID we specified
        Loader<String> loader = loaderManager.getLoader(OPERATION_SEARCH_LOADER);
        // If the Loader was null, initialize it. Else, restart it.
        if(loader==null){
            loaderManager.initLoader(OPERATION_SEARCH_LOADER, queryBundle, this);
        }else{
            loaderManager.restartLoader(OPERATION_SEARCH_LOADER, queryBundle, this);
        }
    }*/

}
