package com.vstechlab.popularmovies.movies;

import android.content.Context;
import android.database.Cursor;

import com.vstechlab.popularmovies.LoadDataView;
import com.vstechlab.popularmovies.Presenter;
import com.vstechlab.popularmovies.data.entity.Movie;

import java.util.List;

/**
 * Interface representing contract between view and presenter.
 */
public interface MoviesContract {

    interface View extends LoadDataView {

        void showMovies(List<Movie> movies);
        void showMovieDetailUI(Movie movie);
        void showFavoriteMovies(Cursor cursor);
        void resetFavoriteMovies();
        void clearMovies();
        void updateMenu();
    }

    interface UserActionListener extends Presenter {
        void loadMoviesSortByPopularity();
        void loadMoviesSortByRatting();
        void loadFavoriteMovies(Context context);
        void openMovieDetails();
    }
}
