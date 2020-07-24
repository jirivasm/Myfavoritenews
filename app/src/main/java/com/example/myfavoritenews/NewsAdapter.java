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

    String completeDate;

    public NewsAdapter(@NonNull Context context, @NonNull List<News> objects) {
        super(context, 0, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_item, parent, false);
        }

        News currNews = getItem(position);
        TextView SectionView = convertView.findViewById(R.id.section_view);
        TextView DateView = convertView.findViewById(R.id.date_view);
        TextView TitleView = convertView.findViewById(R.id.title_view);

        SectionView.setText(currNews.getSection());

        completeDate = currNews.getDate();
        String[] parts = completeDate.split(DATE_SEPARATOR);
        completeDate = parts[0];

        DateView.setText(completeDate);
        TitleView.setText(currNews.getTitle());

        return convertView;
    }
}
