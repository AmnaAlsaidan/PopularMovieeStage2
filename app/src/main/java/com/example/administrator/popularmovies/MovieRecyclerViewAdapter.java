package com.example.administrator.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.popularmovies.DB.FavoriteMovies;
import com.example.administrator.popularmovies.models.Movies;
import com.example.administrator.popularmovies.models.MoviesReviews;
import com.example.administrator.popularmovies.models.MoviesTrailer;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.MoviesViewHolder> {
    private final Context mContext;
    private final static String IMAGE_PATH = "http://image.tmdb.org/t/p/w185//";
    private List<Movies> mMoviesList = new ArrayList<Movies>();
    private List<Movies> mMoviesList2 = new ArrayList<Movies>();
    private List<MoviesTrailer> mMoviesTrailerList = new ArrayList<MoviesTrailer>();
    private List<FavoriteMovies> mFavMoviesList = new ArrayList<FavoriteMovies>();
    private List<MoviesReviews> mMoviesReviewsList = new ArrayList<MoviesReviews>();
    private String mComingFrom = "";
    String poster = "";

    public MovieRecyclerViewAdapter(List<Movies> moviesList, Context context, String comingFrom) {
        mContext = context;
        mMoviesList = moviesList;
        mComingFrom = comingFrom;

    }

    public MovieRecyclerViewAdapter(List<MoviesTrailer> moviesList, Context context) {
        mContext = context;
        mMoviesTrailerList = moviesList;
    }


    public MovieRecyclerViewAdapter(List<MoviesReviews> moviesReviewsList, Context context, String comingFrom, String type) {
        mContext = context;
        mMoviesReviewsList = moviesReviewsList;
        mComingFrom = comingFrom;

    }

    public void setFavMovies(List<Movies> favMovie) {
        mMoviesList = favMovie;
        notifyDataSetChanged();

    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_items, viewGroup, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder moviesViewHolder, int i) {

        switch (mComingFrom) {
            case "MainActivity":
                moviesViewHolder.moviePoster.setVisibility(View.VISIBLE);
                poster = IMAGE_PATH + mMoviesList.get(i).getMoviePoster();
                moviesViewHolder.moviePoster.setImageResource(R.drawable.ic_launcher_foreground);
                Picasso.get().load(poster)
                        .error(R.drawable.not_avilable)
                        .placeholder(R.drawable.placeholder)
                        .fit()
                        .into(moviesViewHolder.moviePoster);
                break;
            case "MoviesReviewsFragment":

                moviesViewHolder.moviePoster.setVisibility(View.GONE);
                moviesViewHolder.authorReview.setVisibility(View.VISIBLE);
                moviesViewHolder.authorName.setVisibility(View.VISIBLE);
                moviesViewHolder.authorName.setText(mMoviesReviewsList.get(i).getAuthor());
                moviesViewHolder.authorReview.setText(mMoviesReviewsList.get(i).getContent());

                break;
            case "":
                moviesViewHolder.moviePoster.setVisibility(View.GONE);
                moviesViewHolder.movieTrailer.setVisibility(View.VISIBLE);
                Picasso.get().load(R.drawable.videothumnail)
                        .error(R.drawable.not_avilable)
                        .placeholder(R.drawable.placeholder)
                        .fit()
                        .into(moviesViewHolder.movieTrailer);
                break;

        }
    }

    @Override
    public int getItemCount() {
        if (mComingFrom.equals("MainActivity")) {
            return mMoviesList.size();

        }
        if (mComingFrom.equals("MoviesReviewsFragment")) {
            return mMoviesReviewsList.size();

        } else {
            return mMoviesTrailerList.size();
        }
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {
        final ImageView moviePoster;
        final ImageView movieTrailer;
        final TextView authorName;
        final TextView authorReview;//= (ExpandableTextView) itemView.findViewById(R.id.authorReview);

        MoviesViewHolder(View itemView) {

            super(itemView);
            authorName = itemView.findViewById(R.id.authorName);
            authorReview = itemView.findViewById(R.id.authorReview);
            moviePoster = itemView.findViewById(R.id.movie_poster_recycler);
            movieTrailer = itemView.findViewById(R.id.movie_trailer_recycler);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (mComingFrom.equalsIgnoreCase("MainActivity")) {
                        Intent intent = new Intent(mContext, MovieDetails.class);
                        intent.putExtra("MoviesList", (Serializable) mMoviesList);
                        intent.putExtra("position", getAdapterPosition());
                        mContext.startActivity(intent);
                    } else if (mComingFrom.equalsIgnoreCase("MoviesReviewsFragment")) {
                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mMoviesReviewsList.get(getAdapterPosition()).getReviewURL())));

                    } else {
                        String key = mMoviesTrailerList.get(getAdapterPosition()).getKey();
                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + key)));

                    }

                }
            });
        }
    }

}



