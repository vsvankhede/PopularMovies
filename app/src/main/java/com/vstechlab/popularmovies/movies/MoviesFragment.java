package com.vstechlab.popularmovies.movies;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;
import com.vstechlab.popularmovies.FavoriteMoviesAdapter;
import com.vstechlab.popularmovies.R;
import com.vstechlab.popularmovies.data.entity.Movie;
import com.vstechlab.popularmovies.data.net.MoviesApi;
import com.vstechlab.popularmovies.movie.MovieActivity;
import com.vstechlab.popularmovies.utils.PreferenceHelper;

import java.util.ArrayList;
import java.util.List;

import static com.vstechlab.popularmovies.data.db.MoviesContract.*;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesFragment extends Fragment implements MoviesContract.View {
    private static final String LOG_TAG = MoviesFragment.class.getSimpleName();

    private MoviesContract.UserActionListener mUserActionListener;
    private ProgressBar mProgressBar;
    private List<Movie> mMoviesList = new ArrayList<>();
    private MoviesAdapter mMoviesAdapter;
    private Menu mMenu;

    public MoviesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_movies, container, false);

        mMoviesAdapter = new MoviesAdapter(getActivity(), mMoviesList);

        GridView gv_movies = (GridView) view.findViewById(R.id.fragment_movies_gv_movies);
        gv_movies.setAdapter(mMoviesAdapter);
        gv_movies.setOnItemClickListener(movieClickListener);

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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Todo get data from shared preference
        if (getSortPreference().equals(MoviesApi.sortByPopularity)) {
            mUserActionListener.loadMoviesSortByPopularity();
        } else if (getSortPreference().equals(MoviesApi.sortByHighRated)) {
            mUserActionListener.loadMoviesSortByRatting();
        }
    }

    @Override
    public void showMovies(List<Movie> movies) {
        mMoviesAdapter.setMovieList(movies);
    }

    @Override
    public void showMovieDetailUI(Movie movie) {

    }

    @Override
    public void clearMovies() {
        mMoviesAdapter.setMovieList(new ArrayList<Movie>(0));
    }

    @Override
    public void updateMenu() {
        if (getSortPreference().equals(MoviesApi.sortByPopularity)) {

            mMenu.findItem(R.id.action_sort_popular).setVisible(false);
            mMenu.findItem(R.id.action_sort_rate).setVisible(true);
            mMenu.findItem(R.id.action_favorite).setVisible(true);
        } else if (getSortPreference().equals(MoviesApi.sortByHighRated)) {

            mMenu.findItem(R.id.action_sort_popular).setVisible(true);
            mMenu.findItem(R.id.action_favorite).setVisible(true);
            mMenu.findItem(R.id.action_sort_rate).setVisible(false);
        } else {
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
            Movie movie = (Movie) adapterView.getItemAtPosition(position);
            startActivity(MovieActivity.getStartIntent(getActivity(), movie));
        }
    };

    private String getSortPreference() {
        return PreferenceHelper.getSortPreference(getActivity());
    }

    private static class MoviesAdapter extends BaseAdapter {
        private Context mContext;
        private List<Movie> mMovieList;

        public MoviesAdapter(Context mContext, List<Movie> movieList) {
            this.mContext = mContext;
            this.mMovieList = movieList;
        }

        @Override
        public int getCount() {
            return mMovieList.size();
        }

        @Override
        public Object getItem(int position) {
            return mMovieList.get(position);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ImageView iv_movie;

            if (view == null) {
                iv_movie = new ImageView(mContext);
                iv_movie.setAdjustViewBounds(true);
            } else {
                iv_movie = (ImageView) view;
            }
            String posterUrl = "http://image.tmdb.org/t/p/w185" + "/" + mMovieList.get(position).getPosterPath();
            Picasso.with(mContext)
                    .load(posterUrl)
                    .resize(185, 278)
                    .into(iv_movie);

            return iv_movie;
        }

        public void setMovieList(List<Movie> movieList) {
            mMovieList = movieList;
            notifyDataSetChanged();
        }
    }
}
