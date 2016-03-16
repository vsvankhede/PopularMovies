package com.vstechlab.popularmovies.movies;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.vstechlab.popularmovies.R;
import com.vstechlab.popularmovies.data.entity.Movie;
import com.vstechlab.popularmovies.data.net.MoviesApi;
import com.vstechlab.popularmovies.movie.MovieActivity;
import com.vstechlab.popularmovies.utils.PreferenceHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesFragment extends Fragment implements MoviesContract.View{
    private static final String LOG_TAG = MoviesFragment.class.getSimpleName();
    public static final int FAVORITE_MOVIES_LOADER = 0;

    private MoviesContract.UserActionListener mUserActionListener;
    private ProgressBar mProgressBar;
    private List<Movie> mMoviesList = new ArrayList<>();
    private MoviesAdapter mMoviesAdapter;
    private FavoriteMoviesAdapter mFavoriteMoviesAdapter;
    private GridView mGvMovies;
    private Menu mMenu;

    public MoviesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_movies, container, false);

        mMoviesAdapter = new MoviesAdapter(getActivity(), mMoviesList);

        mGvMovies = (GridView) view.findViewById(R.id.fragment_movies_gv_movies);
        mGvMovies.setAdapter(mMoviesAdapter);
        mGvMovies.setOnItemClickListener(movieClickListener);

        mProgressBar = (ProgressBar) view.findViewById(R.id.fragment_movies_pgr);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);

        mUserActionListener = new MoviesPresenter(Injection.provideMoviesRepository(), this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mMenu = menu;
        updateMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_sort_popular:
                mUserActionListener.loadMoviesSortByPopularity();
                return true;
            case R.id.action_sort_rate:
                mUserActionListener.loadMoviesSortByRatting();
                return true;
            case R.id.action_favorite:
                mUserActionListener.loadFavoriteMovies(getActivity());
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Todo get data from shared preference
        if (getMoviePreference().equals(MoviesApi.sortByPopularity)) {
            mUserActionListener.loadMoviesSortByPopularity();
        } else if (getMoviePreference().equals(MoviesApi.sortByHighRated)) {
            mUserActionListener.loadMoviesSortByRatting();
        } else if (getMoviePreference().equals(MoviesApi.favorite)) {
            mUserActionListener.loadFavoriteMovies(getActivity());
        }
    }

    @Override
    public void showMovies(List<Movie> movies) {
        mMoviesAdapter.setMovieList(movies);
        if (mGvMovies.getAdapter() != mMoviesAdapter) mGvMovies.setAdapter(mMoviesAdapter);
    }

    @Override
    public void showMovieDetailUI(Movie movie) {

    }

    @Override
    public void showFavoriteMovies(Cursor cursor) {
        mFavoriteMoviesAdapter = new FavoriteMoviesAdapter(getActivity(), cursor, 0);
        mGvMovies.setAdapter(mFavoriteMoviesAdapter);
    }

    @Override
    public void resetFavoriteMovies() {
        mFavoriteMoviesAdapter.swapCursor(null);
    }

    @Override
    public void clearMovies() {
        mMoviesAdapter.setMovieList(new ArrayList<Movie>(0));
    }

    @Override
    public void updateMenu() {
        if (mMenu == null) return;

        if (getMoviePreference().equals(MoviesApi.sortByPopularity)) {

            mMenu.findItem(R.id.action_sort_popular).setVisible(false);
            mMenu.findItem(R.id.action_sort_rate).setVisible(true);
            mMenu.findItem(R.id.action_favorite).setVisible(true);
        } else if (getMoviePreference().equals(MoviesApi.sortByHighRated)) {

            mMenu.findItem(R.id.action_sort_popular).setVisible(true);
            mMenu.findItem(R.id.action_favorite).setVisible(true);
            mMenu.findItem(R.id.action_sort_rate).setVisible(false);
        } else if (getMoviePreference().equals(MoviesApi.favorite)){

            mMenu.findItem(R.id.action_sort_popular).setVisible(true);
            mMenu.findItem(R.id.action_sort_rate).setVisible(true);
            mMenu.findItem(R.id.action_favorite).setVisible(false);
        }
    }

    @Override
    public void showProgressIndicator() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressIndicator() {
        mProgressBar.setVisibility(View.GONE);
    }

    private final GridView.OnItemClickListener movieClickListener = new GridView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            if (adapterView.getAdapter() instanceof MoviesAdapter) {
                Movie movie = (Movie) adapterView.getItemAtPosition(position);
                startActivity(MovieActivity.getStartIntent(getActivity(), movie));
            } else if (adapterView.getAdapter() instanceof FavoriteMoviesAdapter) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                startActivity(MovieActivity.getFavoriteMovieStartIntent(getActivity(), cursor));
            }
        }
    };

    private String getMoviePreference() {
        return PreferenceHelper.getMoviePreference(getActivity());
    }

}
