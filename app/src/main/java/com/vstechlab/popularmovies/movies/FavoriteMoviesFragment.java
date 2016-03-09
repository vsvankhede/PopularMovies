package com.vstechlab.popularmovies.movies;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.vstechlab.popularmovies.FavoriteMoviesAdapter;
import com.vstechlab.popularmovies.R;

public class FavoriteMoviesFragment extends Fragment implements MoviesContract.FavoriteMoviesView{

    private OnFragmentInteractionListener mListener;
    private MoviesContract.FavoriteMoviesUserActionListener mUserActionListener;
    private ProgressBar mProgressBar;
    private FavoriteMoviesAdapter mFavoriteMoviesAdapter;

    public FavoriteMoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_favorite_movies, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);

        mUserActionListener = new FavoriteMoviesPresenter(Injection.provideMoviesRepository(), this);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showMovies(FavoriteMoviesAdapter favoriteMoviesAdapter) {

    }

    @Override
    public void updateMenu() {

    }

    @Override
    public void showProgressIndicator() {

    }

    @Override
    public void hideProgressIndicator() {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
