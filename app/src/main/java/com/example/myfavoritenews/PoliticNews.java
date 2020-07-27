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
 * Use the {@link GeneralNews# newInstance} factory method to
 * create an instance of this fragment.
 */
//This fragment is similar to the others
public class PoliticNews extends Fragment
        implements LoaderManager.LoaderCallbacks<List<News>> {

    //Query for politic news on the US
    private static final String POLITIC_NEWS_REQUEST_URL = "https://content.guardianapis.com/search?page=1&show-tags=contributor&page-size=10&q=us%20politics&api-key=06049af9-0dfd-4848-a341-d13236849462";
    //loader ID
    private static final int POLITIC_NEWS_LOADER = 3;

    //adapter for the list
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
        View rootView = inflater.inflate(R.layout.politic_news, container, false);

        final ListView NewsListView = rootView.findViewById(R.id.politic_list);
        NewsListView.setAdapter(mPoliticAdapter);
        getLoaderManager().restartLoader(POLITIC_NEWS_LOADER, null, this);

        mPoliticAdapter = new NewsAdapter(rootView.getContext(), new ArrayList<News>());


        NewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                News currentNews = mPoliticAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            View listView = rootView.findViewById(R.id.politic_list_displayed);
            listView.setVisibility(View.VISIBLE);

            TextView noConnection = rootView.findViewById(R.id.politic_nothing_to_display);
            noConnection.setVisibility(View.GONE);
            getLoaderManager().initLoader(POLITIC_NEWS_LOADER, null, this);
        } else {
            //Check for no internet while the app is open
            View listView = rootView.findViewById(R.id.politic_list_displayed);
            listView.setVisibility(View.GONE);
            TextView noConnection = rootView.findViewById(R.id.politic_nothing_to_display);
            noConnection.setVisibility(View.VISIBLE);
            noConnection.setText(R.string.no_connection);

            Toast.makeText(rootView.getContext(), R.string.no_connection, Toast.LENGTH_SHORT).show();
            View loadingIndicator = rootView.findViewById(R.id.politic_loading);
            loadingIndicator.setVisibility(View.GONE);
        }

        return rootView;
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {

        //Uri builder for easier url.
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https");
        uriBuilder.authority("content.guardianapis.com");
        uriBuilder.path("search");
        uriBuilder.appendQueryParameter("order-by","newest");
        uriBuilder.appendQueryParameter("show-tags","contributor");
        uriBuilder.appendQueryParameter("page-size","10");
        uriBuilder.appendQueryParameter("page","1");
        uriBuilder.appendQueryParameter("q","us politics");
        uriBuilder.appendQueryParameter("api-key","06049af9-0dfd-4848-a341-d13236849462");

        String url = uriBuilder.toString();
        return new NewsLoader(getActivity(), url);
    }

    @Override
    public void onLoadFinished(@NonNull androidx.loader.content.Loader<List<News>> loader, List<News> data) {
        View loadingIndicator = getActivity().findViewById(R.id.politic_loading);
        View listView = getActivity().findViewById(R.id.sports_list_displayed);
        if (data != null && !data.isEmpty()) {
            final ListView NewsListView = getActivity().findViewById(R.id.politic_list);
            NewsListView.setAdapter(mPoliticAdapter);
            mPoliticAdapter.addAll(data);
        }

        loadingIndicator.setVisibility(View.GONE);


        //check for no news
        if(data != null && data.isEmpty())
        {
            listView.setVisibility(View.GONE);
            TextView noConnection = getActivity().findViewById(R.id.sports_nothing_to_display);
            noConnection.setVisibility(View.VISIBLE);
            noConnection.setText(R.string.no_news);
        }
    }

    @Override
    public void onLoaderReset(@NonNull androidx.loader.content.Loader<List<News>> loader) {
        mPoliticAdapter.clear();
    }
}
