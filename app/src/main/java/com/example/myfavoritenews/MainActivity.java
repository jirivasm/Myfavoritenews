package com.example.myfavoritenews;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find the tabs and the viewpager views
        ViewPager viewPager = findViewById(R.id.viewpager);
        TabLayout tabs = findViewById(R.id.tabs);

        //create the fragment adapter
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());

        //set up the tabs and the viewPager
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
    }
}

//        final ListView NewsListView = findViewById(R.id.list);
//        getLoaderManager().restartLoader(1, null, MainActivity.this);
//        // Create a new {@link ArrayAdapter} of earthquakes
//        mAdapter = new NewsAdapter(this, new ArrayList<News>());
//
//        NewsListView.setAdapter(mAdapter);
//
//        NewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//                News currentEarthquake = mAdapter.getItem(position);
//
//                // Convert the String URL into a URI object (to pass into the Intent constructor)
//                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());
//
//                // Create a new intent to view the earthquake URI
//                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
//                // Send the intent to launch a new activity
//                startActivity(websiteIntent);
//            }
//        });
//        ConnectivityManager connMgr = (ConnectivityManager)
//                getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//
//        if (networkInfo != null && networkInfo.isConnected()) {
//
//                getLoaderManager().initLoader(1, null, this);
//        } else {
//            Toast.makeText(this, R.string.no_connection, Toast.LENGTH_SHORT).show();
//            View loadingIndicator = findViewById(R.id.loading);
//            loadingIndicator.setVisibility(View.GONE);
//        }
//    }
//
//    @Override
//    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
//        return new NewsLoader(this, GENERAL_NEWS_REQUEST_URL);
//    }
//
//    @Override
//    public void onLoadFinished(android.content.Loader<List<News>> loader, List<News> data) {
//        if (mAdapter.isEmpty()) {
//
//            View loadingIndicator = findViewById(R.id.loading);
//            loadingIndicator.setVisibility(View.GONE);
//            mAdapter.addAll(data);
//        }
//    }
//
//    @Override
//    public void onLoaderReset(android.content.Loader<List<News>> loader) {
//        Log.d(LOG_TAG, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH...");
//        mAdapter.clear();
//    }
//}
