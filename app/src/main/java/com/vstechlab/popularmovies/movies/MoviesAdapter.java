package com.vstechlab.popularmovies.movies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.vstechlab.popularmovies.data.entity.Movie;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends BaseAdapter {
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

    public List<Movie> getMovieList() {
        return mMovieList;
    }
}
