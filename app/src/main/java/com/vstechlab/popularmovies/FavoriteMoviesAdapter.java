package com.vstechlab.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.vstechlab.popularmovies.data.db.MoviesContract;
import com.vstechlab.popularmovies.utils.Utils;

public class FavoriteMoviesAdapter extends CursorAdapter {


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
        ivPoster.setLayoutParams(new GridView.LayoutParams(185, 278));

        return ivPoster;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView ivPoster = (ImageView)view;
        ivPoster.setImageBitmap(convertByteArrayToBitmap(cursor));
    }
}
