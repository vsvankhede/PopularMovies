package com.vstechlab.popularmovies.movies;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.vstechlab.popularmovies.data.db.MoviesContract;
import com.vstechlab.popularmovies.utils.Utils;

public class FavoriteMoviesAdapter extends CursorAdapter {
    private static final String LOG_TAG = FavoriteMoviesAdapter.class.getSimpleName();

    public FavoriteMoviesAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    private Bitmap convertByteArrayToBitmap(Cursor cursor){
        int idx_poster = cursor.getColumnIndex(MoviesContract.FavoriteMovies.COLUMN_POSTER);
        return Utils.byteArrayToBitmap(cursor.getBlob(idx_poster));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        ImageView ivPoster = new ImageView(context);
        ivPoster.setAdjustViewBounds(true);
        Log.d(LOG_TAG, "new view created");
        return ivPoster;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView ivPoster = (ImageView)view;
        int idx_poster = cursor.getColumnIndex(MoviesContract.FavoriteMovies.COLUMN_POSTER);
        String imgPath = cursor.getString(idx_poster);
        String posterUrl = "http://image.tmdb.org/t/p/w185" + "/" + imgPath;
        Picasso.with(context)
                .load(posterUrl)
                .resize(185, 278)
                .into(ivPoster);
    }
}
