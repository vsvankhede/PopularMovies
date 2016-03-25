package com.vstechlab.popularmovies.movie;

import android.test.AndroidTestCase;
import android.view.View;
import android.widget.TextView;

import com.vstechlab.popularmovies.R;
import com.vstechlab.popularmovies.data.entity.Review;
import com.vstechlab.popularmovies.utils.TestUtilities;

import java.util.List;

public class ReviewAdapterTest extends AndroidTestCase {
    private static final int EXPECTED_VIEW_TYPE_COUNT = 1;
    private ReviewAdapter mReviewAdapter;
    private List<Review> mTestReviewList = TestUtilities.createReviewList();

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mReviewAdapter = new ReviewAdapter(mContext, mTestReviewList);
    }

    public void testGetViewTypeCount() {
        assertEquals("Error: ReviewAdapter view items are incorrect", EXPECTED_VIEW_TYPE_COUNT,
                mReviewAdapter.getViewTypeCount());
    }

    public void testGetView() throws Exception {
        View view = mReviewAdapter.getView(0, null, null);
        TextView tvTestReviewer = (TextView) view
                .findViewById(R.id.review_item_tv_reviewer_name);
        TextView tvTestReview = (TextView) view
                .findViewById(R.id.review_item_tv_review);

        assertNotNull("Error: Review item is null.", view);
        assertNotNull("Error: Reviewer name is null.", tvTestReviewer.getText());
        assertNotNull("Error: Review is null.", tvTestReview.getText());

        assertEquals("Error: Reviewer name is incorrect.",
                mTestReviewList.get(0).getAuthor(), tvTestReviewer.getText());
        assertEquals("Error: Review content is incorrect.",
                mTestReviewList.get(0).getContent(), tvTestReview.getText());
    }
}