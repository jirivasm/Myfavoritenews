package com.example.myfavoritenews;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class NewsQueryUtils {

    public static final String LOG_TAG = NewsQueryUtils.class.getSimpleName();

    NewsQueryUtils() {
    }

    public static ArrayList<News> extractNews(String jasonResponse) {

        if (TextUtils.isEmpty(jasonResponse)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding news to
        ArrayList<News> newsArrayList = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            //convert SAMPLE_JSON_RESPONSE String into a JSONObject
            JSONObject Objects = new JSONObject(jasonResponse);
            //Extract the Object response that has the array of news(results)
            JSONObject Object = Objects.getJSONObject("response");
            //Extract “results” JSONArray
            JSONArray newsArray = Object.getJSONArray("results");
            //Loop through each feature in the array
            for (int i = 0; i < newsArray.length(); i++) {
                // Get news JSONObject at position i
                JSONObject News = newsArray.getJSONObject(i);

                //Extract “sectionName” for the section name
                String section = News.getString("sectionName");
                // Extract “webPublicationDate” for Complete date and time
                String date = News.getString("webPublicationDate");
                //Extract “webTitle” for title of the news
                String title = News.getString("webTitle");
                //Extract the url of the news site so user can press the news and go to the news and read more
                String url = News.getString("webUrl");
                JSONArray tagsArray = News.getJSONArray("tags");
                //check for author name, separated firs and last name with an "_" so its easier to separate on the newsAdapter
                String authorName = "";
                for (int j = 0; j < tagsArray.length(); j++) {
                    //getting the array for the tags
                    JSONObject author = tagsArray.getJSONObject(j);
                    //getting the info for the first and last name
                    authorName = author.getString("firstName");
                    if(authorName != "") {
                        //sepparating the names with an _ so we can plit it later on the Newsadapter
                        authorName = authorName + "_" + author.getString("lastName");
                    }
                    else
                        authorName = author.getString("lastName");
                }

                //Adding the news to the list
                newsArrayList.add(new News(date, title, section,authorName, url));

            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return newsArrayList;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static List<News> fetchNewsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        List<News> news = extractNews(jsonResponse);

        // Return the {@link Event}
        return news;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        //Get the connection making sure it is successful
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful,
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
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

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
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
