package com.vstechlab.popularmovies.movie;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;

import com.vstechlab.popularmovies.data.db.MoviesContract;
import com.vstechlab.popularmovies.data.db.MoviesContract.FavoriteMovies;
import com.vstechlab.popularmovies.data.entity.Movie;
import com.vstechlab.popularmovies.utils.Utils;

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
    public void saveFavoriteMovie(Movie movie) {
        ContentValues movieValues = new ContentValues();
        movieValues.put(FavoriteMovies.COLUMN_MOVIE_KEY, movie.getId());
        movieValues.put(FavoriteMovies.COLUMN_POSTER, movie.getPosterPath());
        movieValues.put(FavoriteMovies.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        movieValues.put(FavoriteMovies.COLUMN_SUMMARY, movie.getOverview());
        movieValues.put(FavoriteMovies.COLUMN_TITLE, movie.getTitle());
        movieValues.put(FavoriteMovies.COLUMN_VOTE_AVG, movie.getVoteAverage());

        Uri uri = ((MovieFragment)mMoviesView).getActivity().getContentResolver().insert(FavoriteMovies.CONTENT_URI, movieValues);
        long favoriteMovieRowId = ContentUris.parseId(uri);

        String message;
        if (favoriteMovieRowId != -1) {
            message = "Movie added";
        } else {
            message = "Failed to add movie";
        }

        mMoviesView.showToast(message);
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
