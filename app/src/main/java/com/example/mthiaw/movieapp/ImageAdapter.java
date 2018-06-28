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
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
           // imageView.setLayoutParams(new ViewGroup.LayoutParams(85, 85));
            //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else

        {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext).

                load("http://image.tmdb.org/t/p/w185" + mPosterPathsArray.get(position)).

                into(imageView);


        return imageView;


    }
}




































