package com.vstechlab.popularmovies.movie;

import android.test.AndroidTestCase;
import android.view.View;
import android.widget.TextView;

import com.vstechlab.popularmovies.R;
import com.vstechlab.popularmovies.data.db.TestUtilities;
import com.vstechlab.popularmovies.data.entity.Trailer;

import java.util.List;

public class TrailerAdapterTest extends AndroidTestCase{
    private static final int EXPECTED_VIEW_TYPE_COUNT = 1;
    private TrailerAdapter mTrailerAdapter;
    private List<Trailer> mTestTrailerList = TestUtilities.createTrailerList();

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mTrailerAdapter =new TrailerAdapter(mContext, mTestTrailerList);
    }

    public void testGetViewTypeCount() {
        assertEquals("Error: TrailerAdapter view items are incorrect", EXPECTED_VIEW_TYPE_COUNT, mTrailerAdapter.getViewTypeCount());
    }

    public void testGetView() {
        View view = mTrailerAdapter.getView(0, null, null);
        TextView tvTestTitle = (TextView)view
                .findViewById(R.id.trailer_item_tv_title);

        assertNotNull("Error: Trailer item view is null.", view);
        assertNotNull("Error: Trailer item title view is null", tvTestTitle);

        assertEquals("Error: Trailer item title is incorrect",
                mTestTrailerList.get(0).getName(), tvTestTitle.getText());

    }

}
