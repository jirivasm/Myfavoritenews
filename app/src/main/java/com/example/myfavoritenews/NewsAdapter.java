package com.example.myfavoritenews;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    private static final String DATE_SEPARATOR = "T";

    //string to show only the part of the date we want.
    String completeDate;

    //constructor
    public NewsAdapter(@NonNull Context context, @NonNull List<News> objects) {
        super(context, 0, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_item, parent, false);
        }

        //finding the current news and its views
        News currNews = getItem(position);
        TextView SectionView = convertView.findViewById(R.id.section_view);
        TextView DateView = convertView.findViewById(R.id.date_view);
        TextView TitleView = convertView.findViewById(R.id.title_view);

        //setting each section
        SectionView.setText(currNews.getSection());

        //setting the date since the query sends the date and time, we only want the date.
        // the time is not necessary and its separated by the "T" so we use that to separate the date and time
        completeDate = currNews.getDate();
        String[] parts = completeDate.split(DATE_SEPARATOR);
        completeDate = parts[0];

        //set the date with the new String now separated.
        DateView.setText(completeDate);

        //set the title
        TitleView.setText(currNews.getTitle());

        //return the current news
        return convertView;
    }
}
