package com.vstechlab.popularmovies.movie;

import android.graphics.Bitmap;
import android.net.Uri;

import com.vstechlab.popularmovies.LoadDataView;
import com.vstechlab.popularmovies.Presenter;
import com.vstechlab.popularmovies.data.entity.Movie;
import com.vstechlab.popularmovies.data.entity.Review;
import com.vstechlab.popularmovies.data.entity.Trailer;

import java.util.List;

public interface MovieContract {

    interface View extends LoadDataView {

        void showTitle(String title);
        void hideTitle();

        void showPoster(String url);
        void hidePoster();

        void showOverview(String overview);
        void hideOverview();

        void showVoteAverage(Double voteAverage);
        void hideVoteAverage();

        void showReleaseDate(String date);
        void hideReleaseDate();

        void showMovieTrailer(List<Trailer> trailers);

        void showMovieReview(List<Review> reviews);

        void showToast(String message);
    }

    interface UserActionListener extends Presenter {

        void loadMovieDetails(Movie movie);
        void saveFavoriteMovie(Movie movie);
        void loadFavoriteMovieDetails(Uri uri);
        void launchYoutubeVideo(String videoId);
        void readReview(long movieId);
    }
}
