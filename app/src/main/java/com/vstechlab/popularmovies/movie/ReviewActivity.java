package com.vstechlab.popularmovies.movie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.vstechlab.popularmovies.R;
import com.vstechlab.popularmovies.data.entity.Review;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Bundle bundle = getIntent().getExtras();
        ArrayList<Review> reviewArrayList = bundle.getParcelableArrayList(MovieFragment.EXTRA_REVIEW_LIST);

        ReviewAdapter adapter = new ReviewAdapter(this, reviewArrayList);
        ListView lvReview = (ListView) findViewById(R.id.activity_review_lv_reviews);
        lvReview.setAdapter(adapter);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
