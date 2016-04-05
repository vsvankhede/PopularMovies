package com.vstechlab.popularmovies.movies;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.vstechlab.popularmovies.R;
import com.vstechlab.popularmovies.data.db.*;
import com.vstechlab.popularmovies.data.db.MoviesContract;
import com.vstechlab.popularmovies.data.entity.Movie;
import com.vstechlab.popularmovies.movie.MovieActivity;
import com.vstechlab.popularmovies.movie.MovieFragment;

public class MoviesActivity extends AppCompatActivity implements MoviesFragment.Callback{
    private static final String LOG_TAG = MoviesActivity.class.getSimpleName();
    private static final String DETAILFRAGMENT_TAG = "DETAG";
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new MovieFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }

        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieSelected(Movie movie) {
        if (mTwoPane){
            // Replace movie fragment
            Bundle args = new Bundle();
            args.putParcelable(MovieFragment.EXTRA_MOVIE, movie);
            MovieFragment fragment = new MovieFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            // Launch movie fragment
            startActivity(MovieActivity.getStartIntent(this, movie));
        }
    }

    @Override
    public void onFavoriteMovieSelected(Cursor cursor) {
        if (mTwoPane) {
            // Replace movie fragment
            int movieColumnIdx = cursor.getColumnIndex(MoviesContract.FavoriteMovies.COLUMN_MOVIE_KEY);
            Bundle args =  new Bundle();
            Uri movieUri = com.vstechlab.popularmovies.data.db.MoviesContract
                    .FavoriteMovies
                    .buildFavoriteMovieUri(cursor.getInt(movieColumnIdx));
            args.putParcelable(MovieFragment.DETAIL_URI, movieUri);
            MovieFragment fragment = new MovieFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            // Launch movie fragment
            startActivity(MovieActivity.getFavoriteMovieStartIntent(this, cursor));
        }
    }
}
