package com.example.android.newsapp;

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
import java.util.ArrayList;
import java.util.List;

public class NewsQe {
    /**
     * Tag for log messages
     */
    public static final String LOG_TAG = MainActivity.class.getName();


    public static List<News> extractFeatureFromJson(String newsJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding News to
        List<News> news = new ArrayList<>();
        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // build up a list of News objects with the corresponding data.
            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            // Extract the JSONArray associated with the key called "response",
            // which represents a list of response (or News).
            JSONObject newsItem = baseJsonResponse.getJSONObject("response");
            // For each "article" in the NewsArray, create an {@link "article"} object
            JSONArray results = newsItem.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                // Get a single "article" at position i within the list of News.
                JSONObject currentNews = results.getJSONObject(i);

                // Extract the value for the key called "SectionName"
                String SectionName = currentNews.getString("sectionName");
                // Extract the value for the key called webPublicationDate ;
                String time = currentNews.getString("webPublicationDate");
                // Extract the value for the key called webTitle
                String title = currentNews.getString("webTitle");
                // Extract the value for the key called pillarName
                String PillarName = currentNews.getString("pillarName");
                // Extract the value for the key called "url"
                String url = currentNews.getString("webUrl");
                //Extract the value of the author
                JSONArray tagsArray = currentNews.getJSONArray("tags");
                String Author;
                if (tagsArray.length() == 0) {
                    Author = null;
                } else {
                    StringBuilder artAuthorBuilder = new StringBuilder();
                    for (int j = 0; j < tagsArray.length(); j++) {
                        JSONObject objectPositionOne = tagsArray.getJSONObject(j);
                        artAuthorBuilder.append(objectPositionOne.getString("webTitle")).append(". ");
                    }
                    Author = artAuthorBuilder.toString();
                }
                // Create a new {@link news} object with the sectionName, time, title,
                // pillarName and url from the JSON response.
                News newss = new News(SectionName, time, title,Author, PillarName, url);
                news.add(newss);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("NewsQe", "Problem parsing the news JSON results", e);
        }
        // Return the list of news .
        return news;
    }

    /**
     * Query the USGS dataset and return a list of {@link News} objects.
     */
    public static List<News> fetchNewsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link News}s
        List<News> news = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link news}s
        return news;
    }


    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {
            // TODO: Handle the exception
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
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


}