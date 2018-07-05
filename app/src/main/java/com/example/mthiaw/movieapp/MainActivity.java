package com.example.mthiaw.movieapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Make URLs
    private final String TMDB_BASE_URL =
            "https://api.themoviedb.org/3/discover/movie?api_key=";
    private final String MOST_POPULAR_URL_SORT_KEY =
            "&sort_by=popularity.asc";
    private final String TOP_RATED_URL_SORT_KEY =
            "&sort_by=vote_count.asc&vote_count.gte=500";//The vote count from highest to lowest could be consider the top rated
    //API_KEY declaration
    private final String API_KEY = "d430dc6dd7def9959e7a05cf0b77e85d";


    //Declare values
    private int deviceWidth; // this numberOfColumns will be used to determine the number of images we want to see in a row
    private GridView gridView;
    private ArrayList<String> arrayListOfPosters = new ArrayList<>();
    private boolean sortByPopular;
    private boolean sortByTopRated;
    private ImageAdapter imageAdapter;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set the up back button


        //Call the getDeviceWidth on our deviceWidth variable
        deviceWidth = new DeviceSize().getDeviceWidth(getApplicationContext());


        //Add images to the ImageAdapter
        gridView = findViewById(R.id.gridview_id);
        imageAdapter = new ImageAdapter(getApplicationContext(), arrayListOfPosters);
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


    }

    public void loadMovies(String sort) {
        if (isNetworkConnectionAvailable(this)) {
            String url;
            MovieAsyncTask movieAsyncTask = new MovieAsyncTask();

            if (sort == MOST_POPULAR_URL_SORT_KEY) {
                url = TMDB_BASE_URL + API_KEY + sort;
                movieAsyncTask.execute(url);
            } else if (sort == TOP_RATED_URL_SORT_KEY) {
                url = TMDB_BASE_URL + API_KEY + sort;
                movieAsyncTask.execute(url);

            }
        } else {
            noInternetConnection();
        }


    }

    //Check Network Connectivity at the Start of the App
    public boolean isNetworkConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    //2-ON START
    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getString(R.string.app_name));
        MovieAsyncTask movieAsyncTask = new MovieAsyncTask();
        movieAsyncTask.execute(TMDB_BASE_URL + API_KEY);
    }

    //1-MENU
    //Inflating the menu item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    //Do something when item is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String sortType = "";
        switch (item.getItemId()) {

            // Respond to the action bar's Up/Home button


            //If the id=option_menu_most_popular, let's give sortType MOST_POPULAR_URL_SORT_KEY
            //then clear the data, then update the UI
            case R.id.option_menu_most_popular:

                if (sortType != MOST_POPULAR_URL_SORT_KEY) {

                    sortType = MOST_POPULAR_URL_SORT_KEY;
                    setTitle(getString(R.string.most_popular_movie));

                    imageAdapter.clearList();
                    imageAdapter.notifyDataSetChanged();
                    sortByPopular = true;
                    sortByTopRated = false;
                    loadMovies(sortType);
                }


                break;

            //If the id=option_menu_top_rated, let's give sortType TOP_RATED_URL_SORT_KEY
            //then clear the data, then update the UI
            case R.id.option_menu_top_rated:

                if (sortType != TOP_RATED_URL_SORT_KEY) {
                    sortType = TOP_RATED_URL_SORT_KEY;
                    setTitle(getString(R.string.top_rated_movies));

                    imageAdapter.clearList();
                    imageAdapter.notifyDataSetChanged();
                    sortByTopRated = true;
                    sortByPopular = false;
                    loadMovies(sortType);
                }


                break;


        }
        return super.onOptionsItemSelected(item);
    }

    //This method is used to show in the UI that there is no Internet Connection
    private void noInternetConnection() {
        TextView textView = new TextView(getApplicationContext());
        RelativeLayout relativeLayout = findViewById(R.id.relative_layout_id);
        textView.setText(R.string.no_internet_connection);
        if (relativeLayout.getChildCount() == 1) {
            relativeLayout.addView(textView);
        }
        gridView.setVisibility(View.GONE);
    }

    //Creating MovieAsyncTask to call the API in a background thread
    public class MovieAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... strings) {

            String string = strings[0];
            ArrayList<String> arrayListPosterPaths = NetworkUtils.fetchMovieDataFromTMDB(string);
            return arrayListPosterPaths;
        }

        //Now that we have data from the doInBackground, let's pass it to the onPostExecute to do something with it
        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            if (strings != null && !strings.isEmpty()) {
                ImageAdapter imageAdapter = new ImageAdapter(getApplicationContext(), strings);
                gridView.setAdapter(imageAdapter);
            }
        }
    }
}



