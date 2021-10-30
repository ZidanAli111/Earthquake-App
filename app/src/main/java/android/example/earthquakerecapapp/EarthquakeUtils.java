package android.example.earthquakerecapapp;

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

public class EarthquakeUtils {


    public EarthquakeUtils() {
    }



    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("Error", "Error with creating url", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";
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


            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            } else {
                Log.e("Error", "Error response code:" + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("Error", "Problem retreiving the earthquake json response:", e);
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

    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder sb = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
        }
        return sb.toString();
    }

    private static List<Earthquake> ExtractFeaturesFromJson(String earthquakeJSON) {


        if (TextUtils.isEmpty(earthquakeJSON))
            return null;

        List<Earthquake> earthquakeList = new ArrayList<>();

        try {
            JSONObject baseEarthquakeObject = new JSONObject(earthquakeJSON);

            JSONArray featuresArray = baseEarthquakeObject.getJSONArray("features");


            for (int i = 0; i < featuresArray.length(); i++) {

                JSONObject currentEarthquakeObject = featuresArray.getJSONObject(i);

                JSONObject properties = currentEarthquakeObject.getJSONObject("properties");

                double magnitude = properties.getDouble("mag");

                String place = properties.getString("place");

                long time = properties.getLong("time");

                String url = properties.getString("url");


                Earthquake earthquake = new Earthquake(magnitude, place, time, url);

                earthquakeList.add(earthquake);
            }


        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }


        return earthquakeList;

    }


    public static List<Earthquake> fetchEarthquakeData(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);

        } catch (IOException e) {
            Log.e("Error", "Error in input stream", e);
        }
        List<Earthquake> earthquake = ExtractFeaturesFromJson(jsonResponse);

        return earthquake;
    }


}
