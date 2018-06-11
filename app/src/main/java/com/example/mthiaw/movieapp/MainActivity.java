package com.example.mthiaw.movieapp;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Declare values
    private int width; // this width will be more usefull when we include tablet layout in stage 2
    private GridView gridView;
    private ArrayList<String> arrayListOfPosters;
    private boolean sortByPopular;

    
    
    //Make URL
    private String TMDB_BASE_URL=
            "https://api.themoviedb.org/3/discover/movie?api_key=";
    //API_KEY declaration
    private String API_KEY = 
    
    

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Getting the phone width, and placing 3 posters per row on the phone
        Display display = getWindowManager().getDefaultDisplay();
        Point pointSize = new Point();
        display.getSize(pointSize);
        width = pointSize.x/3;




        //Test the ImageAdapter and GridView
        GridView gridview = findViewById(R.id.gridview_id);
        gridview.setColumnWidth(width);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    
    
    
    

    //Inflating the menu item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
    //Do something when item is selected
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int menuItemSelected = item.getItemId();
        if (menuItemSelected == R.id.filter_menu){
            Toast.makeText(this,"Filter Item Selected",Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }
}
