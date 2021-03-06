package com.example.myfavoritenews;


import android.content.Intent;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CovidNews# newInstance} factory method to
 * create an instance of this fragment.
 */
public class CovidNews extends Fragment
        implements LoaderManager.LoaderCallbacks<List<News>> {

    //The query from The Guardian so i get the first 10 news that have covid on them
    private static final String LOG_TAG = NewsLoader.class.getName();

    //number id of the loader
    private static final int COVID_NEWS_LOADER = 1;

    //adapter for the list
    private NewsAdapter mCovidAdapter;

    public CovidNews() {
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
        View rootView = inflater.inflate(R.layout.covid_news, container, false);

        final ListView NewsListView = rootView.findViewById(R.id.covid_list);
        NewsListView.setAdapter(mCovidAdapter);

        //restart loader
        getLoaderManager().restartLoader(COVID_NEWS_LOADER, null, this);
        // Create a new {@link ArrayAdapter} of earthquakes
        mCovidAdapter = new NewsAdapter(rootView.getContext(), new ArrayList<News>());


        NewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                News currentNews = mCovidAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getUrl());

                // Create a new intent to view the news URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        //Getting the connectivity status
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        //if its connected initialize the loader
        // otherwise display a message indicating there is no connection
        if (networkInfo != null && networkInfo.isConnected()) {

            View listView = rootView.findViewById(R.id.covid_list_displayed);
            listView.setVisibility(View.VISIBLE);

            TextView noConnection = rootView.findViewById(R.id.covid_nothing_to_display);
            noConnection.setVisibility(View.GONE);

            getLoaderManager().initLoader(COVID_NEWS_LOADER, null, this);
        } else {

            //Added Check for when there is no internet connection. while using the app
            View listView = rootView.findViewById(R.id.covid_list_displayed);
            listView.setVisibility(View.GONE);
            TextView noConnection = rootView.findViewById(R.id.covid_nothing_to_display);
            noConnection.setVisibility(View.VISIBLE);
            noConnection.setText(R.string.no_connection);

            Toast.makeText(rootView.getContext(), R.string.no_connection, Toast.LENGTH_SHORT).show();
            View loadingIndicator = rootView.findViewById(R.id.covid_loading);
            loadingIndicator.setVisibility(View.GONE);
        }

        return rootView;
    }

    // this function creates the loader and starts to load the news
    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {

        //Uri Builder for easier query url setup
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https");
        uriBuilder.authority("content.guardianapis.com");
        uriBuilder.path("search");
        uriBuilder.appendQueryParameter("order-by", "newest");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("page-size", "10");
        uriBuilder.appendQueryParameter("page", "1");
        uriBuilder.appendQueryParameter("q", "covid");
        uriBuilder.appendQueryParameter("api-key", "06049af9-0dfd-4848-a341-d13236849462");

        String url = uriBuilder.toString();
        return new NewsLoader(getActivity(), url);
    }


    //when the loader is finished add the data to the adapter to be displayed on the list
    //also removes the loading indicator.
    @Override
    public void onLoadFinished(@NonNull androidx.loader.content.Loader<List<News>> loader, List<News> data) {

        View loadingIndicator = getActivity().findViewById(R.id.covid_loading);
        loadingIndicator.setVisibility(View.GONE);
        if (data != null && !data.isEmpty()) {
            final ListView NewsListView = getActivity().findViewById(R.id.covid_list);
            NewsListView.setAdapter(mCovidAdapter);
            mCovidAdapter.addAll(data);
        }

        //Added Check For when there are no news
        View listView = getActivity().findViewById(R.id.covid_list_displayed);
        if (data != null && data.isEmpty()) {
            listView.setVisibility(View.GONE);
            TextView noConnection = getActivity().findViewById(R.id.covid_nothing_to_display);
            noConnection.setVisibility(View.VISIBLE);
            noConnection.setText(R.string.no_news);
        }

    }

    //resets the loader
    @Override
    public void onLoaderReset(@NonNull androidx.loader.content.Loader<List<News>> loader) {
        mCovidAdapter.clear();
    }
}
