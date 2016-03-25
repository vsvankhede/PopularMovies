package com.vstechlab.popularmovies.movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vstechlab.popularmovies.R;
import com.vstechlab.popularmovies.data.entity.Review;

import java.util.List;

public class ReviewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Review> mReviews;
    private LayoutInflater mLayoutInflater;

    public ReviewAdapter(Context mContext, List<Review> mReviews) {
        this.mContext = mContext;
        this.mReviews = mReviews;
        this.mLayoutInflater = (LayoutInflater) this.mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mReviews.size();
    }

    @Override
    public Object getItem(int position) {
        return mReviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder vh;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.review_item, null);
            vh = new ViewHolder();
            vh.tvReviewerName = (TextView) convertView.findViewById(R.id.review_item_tv_reviewer_name);
            vh.tvReview = (TextView) convertView.findViewById(R.id.review_item_tv_review);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Review review = mReviews.get(position);

        if (review != null) {
            vh.tvReviewerName.setText(review.getAuthor());
            vh.tvReview.setText(review.getContent());
        }
        convertView.measure(View.MeasureSpec.makeMeasureSpec(
                        View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        return convertView;
    }

    public static class ViewHolder {
        TextView tvReviewerName;
        TextView tvReview;
    }
}
