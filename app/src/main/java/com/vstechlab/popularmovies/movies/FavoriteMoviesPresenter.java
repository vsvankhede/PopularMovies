package com.vstechlab.popularmovies.movies;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.vstechlab.popularmovies.data.MoviesRepository;

public class FavoriteMoviesPresenter implements MoviesContract.FavoriteMoviesUserActionListener{

    private final MoviesRepository mMoviesRepository;
    private final MoviesContract.FavoriteMoviesView mMoviesView;

    public FavoriteMoviesPresenter(@NonNull MoviesRepository mMoviesRepository,
                                   @NonNull MoviesContract.FavoriteMoviesView mMoviesView) {
        this.mMoviesRepository = mMoviesRepository;
        this.mMoviesView = mMoviesView;
    }


    @Override
    public void loadFavoriteMovies() {

    }

    @Override
    public void openMoviesDetails() {

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
