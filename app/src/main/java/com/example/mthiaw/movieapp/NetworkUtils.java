package com.example.mthiaw.movieapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by mthiaw on 6/7/18.
 * This class will help perform network tasks to communicate with the TMDB API
 */

public class NetworkUtils {
    /** Tag for the log messages */
    public static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    /**
     * Query the TMDB dataset and return String[] object.
     */
    public static String[] fetchMovieDataFromTMDB (String requestUrl) {
        URL url = createUrl(requestUrl);
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response

            String[] dataFethFromTMDB = extractFeatureFromJson(jsonResponse);
            return dataFethFromTMDB;

    }



    //Create URL
    private static URL createUrl(String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful the response code is 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the TMBD JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;

    }
    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return an string object by parsing out information
     * we got from the TMDB request response.
     */
    private static String[] extractFeatureFromJson(String tmdbJson){
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(tmdbJson)) {
            return null;
        }
        try {
            JSONObject baseJsonResponse = new JSONObject(tmdbJson);
            JSONArray resultsArray = baseJsonResponse.getJSONArray("results");

            String[] results = new String[resultsArray.length()];
            //looping through result to get the poster_path
            for (int i = 0; i < resultsArray.length(); i ++){
                JSONObject movie = resultsArray.getJSONObject(i);
                String posterPaths = movie.getString("poster_path");
                results[i]= posterPaths;

                results.
            }

            return results;
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the TMDB JSON results", e);
        }
        return null;
    }
}


/**        private List<String> getMovieDataFromJson(String forecastJsonStr)
 throws JSONException {
 JSONObject movieJson = new JSONObject(forecastJsonStr);
 JSONArray movieArray = movieJson.getJSONArray("results");
 List<String> urls = new ArrayList<>();
 for (int i = 0; i < movieArray.length(); i++) {
 JSONObject movie = movieArray.getJSONObject(i);
 urls.add("http://image.tmdb.org/t/p/w185" + movie.getString("poster_path"));
 }
 return urls;
 }*/