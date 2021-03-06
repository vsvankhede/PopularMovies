package com.vstechlab.popularmovies.movie;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.transition.Scene;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vstechlab.popularmovies.R;
import com.vstechlab.popularmovies.data.entity.Movie;
import com.vstechlab.popularmovies.data.entity.Review;
import com.vstechlab.popularmovies.data.entity.Trailer;
import com.vstechlab.popularmovies.movies.Injection;
import com.vstechlab.popularmovies.movies.MoviesPresenter;
import com.vstechlab.popularmovies.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieFragment extends Fragment implements MovieContract.View {
    private static final String LOG_TAG = MovieFragment.class.getSimpleName();
    public static final int FAVORITE_MOVIES_DETAIL_LOADER = 1;
    public static final String EXTRA_REVIEW_LIST = "EXTRA_REVIEW_LIST";
    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";
    public static final String DETAIL_URI = "URI";
    private MovieContract.UserActionListener mUserActionListener;
    private ShareActionProvider mShareActionProvider;

    private boolean mFavoriteMovie;
    private Button btnFavorite;
    private Button btnReadReview;

    private TextView tvOriginalTitle;
    private TextView tvReleaseDate;
    private TextView tvAvgVote;
    private TextView tvOverview;

    private ImageView ivPoster;
    private ViewStub vsNoMovie;

    private ListView lvTrailer;
    private ScrollView scrMovie;

    private Uri mUri;

    private Movie mMovie;
    private TrailerAdapter mTrailerAdapter;

    public MovieFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        //Todo move onCreate view
        if (savedInstanceState != null) {
            mMovie = savedInstanceState.getParcelable(EXTRA_MOVIE);
            assert mMovie != null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            if (arguments.getParcelable(EXTRA_MOVIE) != null) {
                mMovie = arguments.getParcelable(EXTRA_MOVIE);
                mFavoriteMovie = false;
            } else {
                mUri = arguments.getParcelable(DETAIL_URI);
                mFavoriteMovie = true;
            }
        }

        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        scrMovie = (ScrollView) view.findViewById(R.id.scr_holder);
        btnFavorite = (Button) view.findViewById(R.id.fragment_movie_btn_favorite);
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mFavoriteMovie) {
                    mUserActionListener.saveFavoriteMovie(mMovie);
                }
            }
        });
        btnReadReview = (Button) view.findViewById(R.id.fragment_movie_btn_read_review);
        btnReadReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFavoriteMovie) {
                    long movieId = ((MoviePresenter) mUserActionListener).getMovieId();
                    mUserActionListener.readReview(movieId);
                } else {
                    mUserActionListener.readReview(mMovie.getId());
                }
            }
        });

        tvOriginalTitle = (TextView) view.findViewById(R.id.fragment_movie_tv_title);
        tvReleaseDate = (TextView) view.findViewById(R.id.fragment_movie_tv_release_date);
        tvAvgVote = (TextView) view.findViewById(R.id.fragment_movie_tv_vote);
        ivPoster = (ImageView) view.findViewById(R.id.fragment_movie_iv_poster);
        tvOverview = (TextView) view.findViewById(R.id.fragment_movie_tv_overview);
        lvTrailer = (ListView) view.findViewById(R.id.fragment_movie_lv_trailers);
        lvTrailer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (mTrailerAdapter != null) {
                    Trailer trailer = (Trailer) mTrailerAdapter.getItem(position);
                    mUserActionListener.launchYoutubeVideo(trailer.getKey());
                }
            }
        });
        vsNoMovie = (ViewStub) view.findViewById(R.id.vs_no_movie);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movie, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_share:
                if (mTrailerAdapter != null) {
                    if (!mTrailerAdapter.isEmpty()) {
                        Trailer trailer = (Trailer) mTrailerAdapter.getItem(0);
                        String videoUrl = Utils.createVideoUrl(trailer.getKey());
                        startActivity(Intent
                                .createChooser(createShareMovieIntent
                                        (videoUrl), "Share Trailer!"));
                    }
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private Intent createShareMovieIntent(String url) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
        return shareIntent;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mFavoriteMovie) {
            scrMovie.setVisibility(View.VISIBLE);
            mUserActionListener.loadFavoriteMovieDetails(mUri);
        } else if (mMovie != null) {
            scrMovie.setVisibility(View.VISIBLE);
            mUserActionListener.loadMovieDetails(mMovie);
        } else {
            showNoMovieView();
        }
    }

    private void showNoMovieView() {
        scrMovie.setVisibility(View.GONE);
        vsNoMovie.setLayoutResource(R.layout.no_movie_layout);
        vsNoMovie.inflate();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_MOVIE, mMovie);
        assert mMovie != null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUserActionListener = new MoviePresenter(Injection.provideMoviesRepository(), this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy()");
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
    public void showMovieTrailer(List<Trailer> trailers) {
        if (trailers != null) {
            mTrailerAdapter = new TrailerAdapter(this.getActivity(), trailers);
            lvTrailer.setAdapter(mTrailerAdapter);
        }
    }

    @Override
    public void showMovieReview(List<Review> reviews) {
        ArrayList<Review> reviewArrayList = new ArrayList<Review>(reviews);
        Intent intent = new Intent(this.getActivity(), ReviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(EXTRA_REVIEW_LIST, reviewArrayList);
        intent.putExtras(bundle);
        this.getActivity().startActivity(intent);
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
