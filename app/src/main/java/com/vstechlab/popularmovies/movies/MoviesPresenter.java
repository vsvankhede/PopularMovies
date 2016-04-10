package com.vstechlab.popularmovies.movies;

import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;

import com.vstechlab.popularmovies.data.MoviesRepository;
import com.vstechlab.popularmovies.data.entity.MovieList;
import com.vstechlab.popularmovies.data.net.MoviesApi;
import com.vstechlab.popularmovies.utils.PreferenceHelper;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MoviesPresenter implements MoviesContract.UserActionListener, LoaderManager.LoaderCallbacks<Cursor>  {

    private final MoviesRepository mMoviesRepository;
    private final MoviesContract.View mMoviesView;

    public MoviesPresenter(@NonNull MoviesRepository mMoviesRepository, @NonNull MoviesContract.View mMoviesView) {
        // Todo: null check
        this.mMoviesRepository = mMoviesRepository;
        this.mMoviesView = mMoviesView;
    }


    @Override
    public void loadMoviesSortByPopularity() {
        mMoviesView.clearMovies();
        mMoviesView.showProgressIndicator();
        Call<MovieList> call = mMoviesRepository.getMoviesSortByPopularity();
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Response<MovieList> response, Retrofit retrofit) {

                MovieList movieList = response.body();

                PreferenceHelper.setMoviePreference(((MoviesFragment) mMoviesView).getActivity(),
                        MoviesApi.sortByPopularity);
                mMoviesView.updateMenu();
                mMoviesView.hideProgressIndicator();
                mMoviesView.showMovies(movieList.getResults());
            }

            @Override
            public void onFailure(Throwable t) {
                if (t instanceof IOException) {
                    mMoviesView.hideProgressIndicator();
                    mMoviesView.showNoInternetView();
                }
            }
        });
    }

    @Override
    public void loadMoviesSortByRatting() {
        mMoviesView.clearMovies();
        mMoviesView.showProgressIndicator();
        Call<MovieList> call = mMoviesRepository.getMoviesSortByRatting();
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Response<MovieList> response, Retrofit retrofit) {

                MovieList movieList = response.body();

                PreferenceHelper.setMoviePreference(((MoviesFragment) mMoviesView).getActivity(),
                        MoviesApi.sortByHighRated);

                mMoviesView.updateMenu();
                mMoviesView.hideProgressIndicator();
                mMoviesView.showMovies(movieList.getResults());
            }

            @Override
            public void onFailure(Throwable t) {
                if (t instanceof IOException) {
                    mMoviesView.hideProgressIndicator();
                    mMoviesView.showNoInternetView();
                }
            }
        });

    }

    @Override
    public void loadFavoriteMovies(Context context) {
        ((MoviesFragment)mMoviesView).getLoaderManager().initLoader(MoviesFragment.FAVORITE_MOVIES_LOADER, null, this);
    }

    @Override
    public void openMovieDetails() {

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
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mMoviesRepository.getFavoriteMovies(((MoviesFragment)mMoviesView).getActivity());
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        PreferenceHelper.setMoviePreference(((MoviesFragment) mMoviesView).getActivity(),
                MoviesApi.favorite);
        mMoviesView.showFavoriteMovies(data);
        mMoviesView.updateMenu();
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        mMoviesView.resetFavoriteMovies();
    }
}
