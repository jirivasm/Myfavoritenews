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
public class GeneralNews extends Fragment
        implements LoaderManager.LoaderCallbacks<List<News>> {

    //query from The Guardian to find the latest news up to 10.
    private static final String GENERAL_NEWS_REQUEST_URL = "https://content.guardianapis.com/search?order-by=newest&page-size=10&page=1&api-key=06049af9-0dfd-4848-a341-d13236849462";

    //the Id of the Loader
    private static final int GENERAL_NEWS_LOADER = 0;

    //adapter for the list
    private NewsAdapter mGeneralAdapter;

    public GeneralNews() {
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
        View rootView = inflater.inflate(R.layout.general_news, container, false);

        //set the list and the adapter
        final ListView NewsListView = rootView.findViewById(R.id.general_list);
        NewsListView.setAdapter(mGeneralAdapter);
        getLoaderManager().restartLoader(GENERAL_NEWS_LOADER, null, this);

        mGeneralAdapter = new NewsAdapter(rootView.getContext(), new ArrayList<News>());


        NewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                News currentNews = mGeneralAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getUrl());

                // Create a new intent to view the news URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            getLoaderManager().initLoader(GENERAL_NEWS_LOADER, null, this);
        } else {
            Toast.makeText(rootView.getContext(), R.string.no_connection, Toast.LENGTH_SHORT).show();
            View loadingIndicator = rootView.findViewById(R.id.general_loading);
            loadingIndicator.setVisibility(View.GONE);
        }

        return rootView;
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(getActivity(), GENERAL_NEWS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull androidx.loader.content.Loader<List<News>> loader, List<News> data) {
        View loadingIndicator = getActivity().findViewById(R.id.general_loading);
        if (mGeneralAdapter.isEmpty()) {
            final ListView NewsListView = getActivity().findViewById(R.id.general_list);
            NewsListView.setAdapter(mGeneralAdapter);
            mGeneralAdapter.addAll(data);
        }
        loadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull androidx.loader.content.Loader<List<News>> loader) {
        mGeneralAdapter.clear();
    }
}
