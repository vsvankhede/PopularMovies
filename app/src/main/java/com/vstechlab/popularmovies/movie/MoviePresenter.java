package com.vstechlab.popularmovies.movie;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import com.vstechlab.popularmovies.data.MoviesRepository;
import com.vstechlab.popularmovies.data.db.MoviesContract;
import com.vstechlab.popularmovies.data.db.MoviesContract.FavoriteMovies;
import com.vstechlab.popularmovies.data.entity.Movie;
import com.vstechlab.popularmovies.movies.MoviesFragment;
import com.vstechlab.popularmovies.utils.Utils;

import java.text.DecimalFormat;

public class MoviePresenter implements MovieContract.UserActionListener, LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = MoviePresenter.class.getSimpleName();

    private final MoviesRepository mMoviesRepository;
    private final MovieContract.View mMoviesView;
    private Uri mUri;

    public MoviePresenter(@NonNull MoviesRepository mMoviesRepository, @NonNull MovieContract.View mMoviesView) {
        this.mMoviesView = mMoviesView;
        this.mMoviesRepository = mMoviesRepository;
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

        Uri uri = ((MovieFragment) mMoviesView).getActivity().getContentResolver().insert(FavoriteMovies.CONTENT_URI, movieValues);
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
    public void loadFavoriteMovieDetails(Uri uri) {
        mUri = uri;
        ((MovieFragment) mMoviesView).getLoaderManager().initLoader(
                MovieFragment.FAVORITE_MOVIES_DETAIL_LOADER,
                null,
                this);
        Log.d(LOG_TAG, "loadFavoriteMovieDetails.uri: " + uri.toString());
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mMoviesRepository.getFavoriteMovieDetail(((MovieFragment) mMoviesView).getActivity(),
                mUri);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            int COL_MOVIE_TITLE = data.getColumnIndex(FavoriteMovies.COLUMN_TITLE);
            int COL_MOVIE_POSTER = data.getColumnIndex(FavoriteMovies.COLUMN_POSTER);
            int COL_RELEASE_DATE = data.getColumnIndex(FavoriteMovies.COLUMN_RELEASE_DATE);
            int COL_AVERAGE_VOTE = data.getColumnIndex(FavoriteMovies.COLUMN_VOTE_AVG);
            int COL_OVERVIEW = data.getColumnIndex(FavoriteMovies.COLUMN_SUMMARY);

            mMoviesView.showOverview(data.getString(COL_OVERVIEW));
            mMoviesView.showPoster(data.getString(COL_MOVIE_POSTER));
            mMoviesView.showTitle(data.getString(COL_MOVIE_TITLE));
            mMoviesView.showReleaseDate(data.getString(COL_RELEASE_DATE));
            mMoviesView.showVoteAverage(Double.parseDouble(data.getString(COL_AVERAGE_VOTE)));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
