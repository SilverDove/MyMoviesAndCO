package com.example.mymoviesco.view;

public class Items {
    private int mImage;
    private String mTitle;
    private String mDescription;

    public Items(int image, String title, String description){
        mImage=image;
        mTitle=title;
        mDescription=description;
    }

    public int getImage(){
        return mImage;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getDescription(){
        return mDescription;
    }
}
