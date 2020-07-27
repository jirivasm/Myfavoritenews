package com.example.myfavoritenews;

//class to create a signle news object
public class News {

    //needed for each news the section,date,title and url
    private String mSection;
    private String mDate;
    private String mTitle;
    private String mUrl;
    private String mAuthor;

    //constructor to set up the news
    News(String date, String title, String section, String Author,String Url) {
        mDate = date;
        mTitle = title;
        mSection = section;
        mAuthor = Author;
        mUrl = Url;

    }

    //getter methods to acces the data
    public String getDate() {
        return mDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSection() {
        return mSection;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getAuthor() {
        return mAuthor;
    }
}
