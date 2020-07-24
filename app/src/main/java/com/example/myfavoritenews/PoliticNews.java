package com.example.myfavoritenews;


import android.content.Intent;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;


import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GeneralNews# newInstance} factory method to
 * create an instance of this fragment.
 */
public class PoliticNews extends Fragment
        implements LoaderManager.LoaderCallbacks<List<News>>{

    private static final String POLITIC_NEWS_REQUEST_URL = "https://content.guardianapis.com/search?page=1&page-size=10&q=us%20politics&api-key=06049af9-0dfd-4848-a341-d13236849462";
    private static final int POLITIC_NEWS_LOADER = 3;

    private NewsAdapter mPoliticAdapter;

    public PoliticNews() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.politic_news, container, false);

        final ListView NewsListView = rootView.findViewById(R.id.politic_list);
        NewsListView.setAdapter(mPoliticAdapter);
        getLoaderManager().restartLoader(POLITIC_NEWS_LOADER, null,  this);

        mPoliticAdapter = new NewsAdapter(rootView.getContext(), new ArrayList<News>());


        NewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                News currentEarthquake = mPoliticAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            getLoaderManager().initLoader(POLITIC_NEWS_LOADER, null,  this);
        } else {
            Toast.makeText(rootView.getContext(), R.string.no_connection, Toast.LENGTH_SHORT).show();
            View loadingIndicator = rootView.findViewById(R.id.politic_loading);
            loadingIndicator.setVisibility(View.GONE);
        }

        return rootView;
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(getActivity(), POLITIC_NEWS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull androidx.loader.content.Loader<List<News>> loader, List<News> data) {
        View loadingIndicator = getActivity().findViewById(R.id.politic_loading);
        if (mPoliticAdapter.isEmpty()) {
            final ListView NewsListView = getActivity().findViewById(R.id.politic_list);
            NewsListView.setAdapter(mPoliticAdapter);
            mPoliticAdapter.addAll(data);
        }
        loadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull androidx.loader.content.Loader<List<News>> loader) {
        mPoliticAdapter.clear();
    }
}
