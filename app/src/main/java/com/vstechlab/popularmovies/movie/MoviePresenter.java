package com.vstechlab.popularmovies.movie;

import android.util.Log;

import com.vstechlab.popularmovies.data.entity.Movie;

import java.text.DecimalFormat;

public class MoviePresenter implements MovieContract.UserActionListener {
    private static final String LOG_TAG = MoviePresenter.class.getSimpleName();

    private final MovieContract.View mMoviesView;

    public MoviePresenter(MovieContract.View mMoviesView) {
        this.mMoviesView = mMoviesView;
    }

    @Override
    public void loadMovieDetails(Movie movie) {
        mMoviesView.showTitle(movie.getOriginalTitle());
        mMoviesView.showPoster(movie.getPosterPath());
        mMoviesView.showReleaseDate(movie.getReleaseDate());
        mMoviesView.showVoteAverage(movie.getVoteAverage());
        mMoviesView.showOverview(movie.getOverview());
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }
}
