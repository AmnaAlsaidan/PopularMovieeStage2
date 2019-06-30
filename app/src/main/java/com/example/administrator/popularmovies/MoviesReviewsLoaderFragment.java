package com.example.administrator.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.popularmovies.models.MoviesReviews;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import static android.widget.LinearLayout.HORIZONTAL;

public class MoviesReviewsLoaderFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<MoviesReviews>> {
    private RecyclerView recyclerView;

    String MovieDB_Reviews;
    int movieId;
    TextView noReviews;
    List<MoviesReviews> moviesReviewsList;


    @Override
    public void onLoadFinished(Loader<List<MoviesReviews>> loader, List<MoviesReviews> o) {
        if (moviesReviewsList.isEmpty()) {
            noReviews.setVisibility(View.VISIBLE);
        } else {
            MovieRecyclerViewAdapter movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(moviesReviewsList, getContext(), "MoviesReviewsFragment", "");
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), HORIZONTAL);
            recyclerView.addItemDecoration(itemDecor);
            recyclerView.setAdapter(movieRecyclerViewAdapter);
            recyclerView.setLayoutManager(layoutManager);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<MoviesReviews>> loader) {

    }

    @Override
    public Loader<List<MoviesReviews>> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<List<MoviesReviews>>(getContext()) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
            }

            @Override
            public List<MoviesReviews> loadInBackground() {
                try {
                    // https://api.themoviedb.org/3/movie/popular?api_key=ce4bc4732cb010a2aeedb4eaf55e9557
                    String json2 = NetworkUtils.getResponseFromHttpUrl(MovieDB_Reviews);
                    moviesReviewsList = JsonUtils.parseMovieReviewsJson(json2);
                    if (moviesReviewsList == null) {
                        return null;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return moviesReviewsList;
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reviews_fragment, container, false);
        Bundle bundle = this.getArguments();
        noReviews = view.findViewById(R.id.noReviews);
        movieId = bundle.getInt("movieId");
        MovieDB_Reviews = "https://api.themoviedb.org/3/movie/" + movieId + "/reviews?" + MainActivity.API_KEY;
        getActivity().getSupportLoaderManager().initLoader(2, null, (LoaderManager.LoaderCallbacks<List<MoviesReviews>>) this).forceLoad();
        recyclerView = view.findViewById(R.id.recyclerViewInFragment);
        return view;
    }
}