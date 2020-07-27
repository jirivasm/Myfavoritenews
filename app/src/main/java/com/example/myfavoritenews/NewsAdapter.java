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
    private static final String NAME_SEPARATOR = "_";

    //string to show only the part of the date we want.
    String completeDate;
    String authorName;

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
        TextView AuthorView = convertView.findViewById(R.id.author_view);

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


        //Author Name and check for when its only first name or last name
        // and if there is no author name don't show
        authorName = currNews.getAuthor();
        String[] nameParts = authorName.split(NAME_SEPARATOR);

        //if there is a first and last name
        if(nameParts.length ==2) {
            AuthorView.setVisibility(View.VISIBLE);
            if (nameParts[1] != "" && nameParts[0] != "") {
                authorName = nameParts[1] + ", " + nameParts[0];
            }
        }
        else if(nameParts.length ==1 && authorName != ""){
            AuthorView.setVisibility(View.VISIBLE);
            //otherwise make the author name to be the first name or the last name whichever fits.
            authorName = nameParts[0];
        }
        else {
            AuthorView.setVisibility(View.GONE);
        }

        AuthorView.setText(authorName);

        //return the current news
        return convertView;
    }
}
