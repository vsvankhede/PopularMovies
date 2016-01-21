package com.vstechlab.popularmovies.movies;

import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.vstechlab.popularmovies.data.MoviesRepository;
import com.vstechlab.popularmovies.data.entity.MovieList;
import com.vstechlab.popularmovies.data.net.MoviesApi;
import com.vstechlab.popularmovies.utils.PreferenceHelper;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MoviesPresenter implements MoviesContract.UserActionListener {

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

                PreferenceHelper.setSortPreference(((MoviesFragment)mMoviesView).getActivity(),
                        MoviesApi.sortByPopularity);
                mMoviesView.updateMenu();
                mMoviesView.hideProgressIndicator();
                mMoviesView.showMovies(movieList.getResults());
            }

            @Override
            public void onFailure(Throwable t) {

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

                PreferenceHelper.setSortPreference(((MoviesFragment)mMoviesView).getActivity(),
                        MoviesApi.sortByHighRated);

                mMoviesView.updateMenu();
                mMoviesView.hideProgressIndicator();
                mMoviesView.showMovies(movieList.getResults());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

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
}
