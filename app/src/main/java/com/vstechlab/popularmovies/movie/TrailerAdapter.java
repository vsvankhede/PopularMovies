package com.vstechlab.popularmovies.movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vstechlab.popularmovies.R;
import com.vstechlab.popularmovies.data.entity.Trailer;

import java.util.List;

public class TrailerAdapter extends BaseAdapter {
    private Context mContext;
    private List<Trailer> mTrailers;
    private LayoutInflater mLayoutInflater;

    public TrailerAdapter(Context context, List<Trailer> mTrailers) {
        this.mContext = context;
        this.mTrailers = mTrailers;
        this.mLayoutInflater = (LayoutInflater)this.mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mTrailers.size();
    }

    @Override
    public Object getItem(int position) {
        return mTrailers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder vh;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.trailer_item, null);
            vh = new ViewHolder();
            vh.tvTrailerTitle = (TextView) convertView.findViewById(R.id.trailer_item_tv_title);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Trailer trailer = mTrailers.get(position);

        if (trailer != null) {
            vh.tvTrailerTitle.setText(trailer.getName());
        }

        return convertView;
    }

    public static class ViewHolder {
        TextView tvTrailerTitle;
    }
}
