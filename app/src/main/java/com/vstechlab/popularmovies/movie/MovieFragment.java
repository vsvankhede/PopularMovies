package com.vstechlab.popularmovies.movie;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vstechlab.popularmovies.R;
import com.vstechlab.popularmovies.data.entity.Movie;
import com.vstechlab.popularmovies.movies.MoviesContract;
import com.vstechlab.popularmovies.utils.Utils;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieFragment extends Fragment implements MovieContract.View {
    private static final String LOG_TAG = MovieFragment.class.getSimpleName();

    private Movie mMovie;

    private MovieContract.UserActionListener mUserActionListener;

    private Button btnFavorite;

    private TextView tvOriginalTitle;
    private TextView tvReleaseDate;
    private TextView tvAvgVote;
    private TextView tvOverview;

    private ImageView ivPoster;

    public MovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovie = getActivity().getIntent().getParcelableExtra(MovieActivity.EXTRA_MOVIE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        btnFavorite = (Button) view.findViewById(R.id.fragment_movie_btn_favorite);
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = ((BitmapDrawable) ivPoster.getDrawable()).getBitmap();
                mUserActionListener.saveFavoriteMovie(mMovie);
            }
        });

        tvOriginalTitle = (TextView) view.findViewById(R.id.fragment_movie_tv_title);
        tvReleaseDate = (TextView) view.findViewById(R.id.fragment_movie_tv_release_date);
        tvAvgVote = (TextView) view.findViewById(R.id.fragment_movie_tv_vote);
        ivPoster = (ImageView) view.findViewById(R.id.fragment_movie_iv_poster);
        tvOverview = (TextView) view.findViewById(R.id.fragment_movie_tv_overview);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        mUserActionListener.loadMovieDetails(mMovie);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        mUserActionListener = new MoviePresenter(this);
    }

    @Override
    public void showTitle(String title) {
        tvOriginalTitle.setText(title);
    }

    @Override
    public void hideTitle() {

    }

    @Override
    public void showPoster(String url) {
        String posterUrl = "http://image.tmdb.org/t/p/w185" + "/" + url;
        Picasso.with(getActivity())
                .load(posterUrl)
                .resize(185, 278)
                .into(ivPoster);
    }

    @Override
    public void hidePoster() {

    }

    @Override
    public void showOverview(String overview) {
        tvOverview.setText(overview);
    }

    @Override
    public void hideOverview() {

    }

    @Override
    public void showVoteAverage(Double voteAverage) {
        tvAvgVote.setText(String.valueOf(voteAverage));
    }

    @Override
    public void hideVoteAverage() {

    }

    @Override
    public void showReleaseDate(String date) {
        tvReleaseDate.setText(date);
    }

    @Override
    public void hideReleaseDate() {

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressIndicator() {

    }

    @Override
    public void hideProgressIndicator() {

    }
}
