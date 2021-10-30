package android.example.earthquakerecapapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String JSON_RESPONSE = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=1&limit=50";

    private EarthquakeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView earthquakeListView = findViewById(R.id.list);

        adapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());


        earthquakeListView.setAdapter(adapter);


        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake currentEarthquake = adapter.getItem(position);

                Uri earthquakeUri = Uri.parse(currentEarthquake.getmUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                startActivity(websiteIntent);
            }
        });

        EarthquakeTask earthquakeTask = new EarthquakeTask();

        earthquakeTask.execute(JSON_RESPONSE);


    }


    private class EarthquakeTask extends AsyncTask<String, Void, List<Earthquake>> {


        @Override
        protected List<Earthquake> doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            Log.i("URLS", "GIVEN JSONUrl is:" + urls[0]);

            List<Earthquake> result = EarthquakeUtils.fetchEarthquakeData(urls[0]);


            return result;

        }

        @Override
        protected void onPostExecute(List<Earthquake> data) {

            adapter.clear();


            if (data != null && !data.isEmpty()) {
                adapter.addAll(data);
            }
        }
    }

}