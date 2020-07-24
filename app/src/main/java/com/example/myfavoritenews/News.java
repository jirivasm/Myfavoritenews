package com.example.myfavoritenews;

public class News {

    private String mSection;
    private String mDate;
    private String mTitle;
    private String mUrl;

    News(String date, String title, String section,String Url)
    {
        mDate = date;
        mTitle = title;
        mSection = section;
        mUrl = Url;
    }

    public String getDate(){return mDate;}
    public String getTitle(){return mTitle;}
    public String getSection(){return mSection;}
    public String getUrl(){return mUrl;}

}
