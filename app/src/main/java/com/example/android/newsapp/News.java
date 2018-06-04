package com.example.android.newsapp;

public class News {

    private String mSectionName;
    private String mTime;
    private String mTitle;
    private String mAuthor;
    private String mPillarName;
    private String mUrl;

    public News(String SectionName, String time, String title,String Author, String pillarName, String url) {
        mSectionName = SectionName;
        mTime = time;
        mTitle = title;
        mAuthor = Author;
        mPillarName = pillarName;
        mUrl = url;
    }

    public String getmSectionName() {
        return mSectionName;
    }

    public String getmTime() {
        return mTime;
    }

    public String getmTitile() {
        return mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmPillarName() {
        return mPillarName;
    }

    public String getmUrl() {
        return mUrl;
    }
}


