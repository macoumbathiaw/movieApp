package com.example.mthiaw.movieapp;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.example.mthiaw.movieapp.MainActivity;

public class DeviceSize {

    private int deviceWith;//The deviceWidth value will give us the number of columns in UI when displaying the movie
    Context mContext;
    Point pointSize;


    public DeviceSize() {

    }




    public int getDeviceWith() {

        //Getting the device size
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size;
        size = getPointSize();
        display.getSize(size);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);


        float density = mContext.getResources().getDisplayMetrics().density;
        float dpHeight = displayMetrics.heightPixels / density;
        float dpWidth = displayMetrics.widthPixels /density;

        int width = (int) (dpWidth);
        int height = (int) (dpHeight);

        int smallestWidth = Math.min(width, height);


        if(smallestWidth > 600 || smallestWidth > 720)
        {
            //The below values (3 & 6) will give us the number of columns in UI when displaying the movie
            deviceWith = size.x/6;
        }
        else deviceWith=size.x/3;



        return deviceWith;
    }



    public Point getPointSize() {
        Point pointSize = new Point();
        return pointSize;
    }





}
