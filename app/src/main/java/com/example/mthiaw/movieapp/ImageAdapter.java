package com.example.mthiaw.movieapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mPosterPathsArray;
    private int deviceWidth;

   //1- private int numberOfColumns; // this numberOfColumns will be used to determine the number of images we want to see in a row



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
        //ImageView imageView;
        ImageView imageView = new ImageView(mContext);
        deviceWidth = new DeviceSize().getDeviceWith();
        if (convertView == null) {





           /** WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getMetrics(displayMetrics);





            float density = mContext.getResources().getDisplayMetrics().density;
            float dpHeight = displayMetrics.heightPixels / density;
            float dpWidth = displayMetrics.widthPixels /density;

            int width = (int) (dpWidth);
            int height = (int) (dpHeight);


            Point pointSize = new Point();
            display.getSize(pointSize);
            numberOfColumns =  pointSize.x/3;

            // if it's not recycled, initialize some attributes

            //imageView.setLayoutParams(new ViewGroup.LayoutParams(width, height));
            //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);*/

        } else

        {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext).

                load("http://image.tmdb.org/t/p/w185" + mPosterPathsArray.get(position))
                //.fit()
                //.centerCrop()
                .resize(deviceWidth,(int)(deviceWidth*1.5))
                .into(imageView);


        return imageView;


    }


}




































