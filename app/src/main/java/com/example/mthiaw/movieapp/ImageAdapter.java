package com.example.mthiaw.movieapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mPosterPathsArray;
    private int deviceWidth;

    //Constructor
    public ImageAdapter(Context context, ArrayList<String> posterPathsArray) {
        this.mContext = context;
        this.mPosterPathsArray = posterPathsArray;
    }

    @Override
    public int getCount() {
        return mPosterPathsArray.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        deviceWidth = new DeviceSize().getDeviceWidth(mContext);
        if (convertView == null) {
            imageView = new ImageView(mContext);

        } else

        {
            imageView = (ImageView) convertView;
        }
        //Using the Picasso library to load the thumbnails.
        // Each will take the device width as width and (device width )*1.5 as height
        //This will allow the thumbnails to perfectly fit on the screeen
        Picasso.with(mContext).

                load("http://image.tmdb.org/t/p/w185" + mPosterPathsArray.get(position))
                .resize(deviceWidth, (int) (deviceWidth * 1.5))
                //Adding a loading image placeholder
                .placeholder(R.mipmap.loading_placeholder)
                .into(imageView);


        return imageView;


    }

    //Clear the list of poster path
    public void clearList() {
        mPosterPathsArray.clear();
    }


}




































