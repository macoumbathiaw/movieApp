package com.example.mthiaw.movieapp;

import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Make URL
    private final String TMDB_BASE_URL =
            "https://api.themoviedb.org/3/discover/movie?api_key=";
    //API_KEY declaration
    private final String API_KEY = "";
    //Declare values
    private int deviceWidth; // this numberOfColumns will be used to determine the number of images we want to see in a row
    private GridView gridView;
    private ArrayList<String> arrayListOfPosters = new ArrayList<String>();
    private boolean sortByPopular;
    private boolean sortByTopRated;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Call the getDeviceWidth on our deviceWidth variable
        deviceWidth = new DeviceSize().getDeviceWidth(getApplicationContext());


        //Add images to the ImageAdapter
        gridView = findViewById(R.id.gridview_id);
        ImageAdapter imageAdapter = new ImageAdapter(getApplicationContext(), arrayListOfPosters);
        gridView.setColumnWidth(deviceWidth);
        gridView.setAdapter(imageAdapter);

        //Click on the gridView items
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //Below is the code that allows the transition to another page
                //through intent
            }
        });

        //Check to see if the Network connection is available, if so, run to execute the NetworkUtils
        if (isNetworkConnectionAvailable(this)) {
            MovieAsyncTask task = new MovieAsyncTask();
            task.execute(TMDB_BASE_URL + API_KEY);

        } else {
            //If the network connection is not working we will display a text saying "No Internet Connection"
            //and make the whole gridView invisible
            TextView textView = new TextView(getApplicationContext());
            RelativeLayout relativeLayout = findViewById(R.id.relative_layout_id);
            textView.setText("You are not connected to the internet");
            if (relativeLayout.getChildCount() == 1) {
                relativeLayout.addView(textView);
            }
            gridView.setVisibility(View.GONE);

        }


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
        if (menuItemSelected == R.id.filter_menu) {
            Toast.makeText(getApplicationContext(), "Filter Item Selected", Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }

    //Check Network Connectivity at the Start of the App
    public boolean isNetworkConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    //Creating MovieAsyncTask to call the API in a background thread
    public class MovieAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {


        @Override
        protected ArrayList<String> doInBackground(String... strings) {

            String string = strings[0];
            ArrayList<String> arrayListPosterPaths = NetworkUtils.fetchMovieDataFromTMDB(string);
            return arrayListPosterPaths;
        }


        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            if (strings != null && !strings.isEmpty()) {
                ImageAdapter imageAdapter = new ImageAdapter(getApplicationContext(), strings);
                gridView.setAdapter(imageAdapter);
            }
        }
    }
}



