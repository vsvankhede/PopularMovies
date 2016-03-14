package com.vstechlab.popularmovies.movies;

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
        void clearMovies();
        void updateMenu();
    }

    interface FavoriteMoviesView extends LoadDataView {
        void showMovies(FavoriteMoviesAdapter favoriteMoviesAdapter);
        void updateMenu();
    }

    interface UserActionListener extends Presenter {
        void loadMoviesSortByPopularity();
        void loadMoviesSortByRatting();
        void loadFavoriteMovies();
        void openMovieDetails();
    }

    interface FavoriteMoviesUserActionListener extends Presenter {
        void loadFavoriteMovies();
        void openMoviesDetails();
    }
}
