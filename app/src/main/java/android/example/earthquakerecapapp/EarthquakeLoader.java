package android.example.earthquakerecapapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {


    private String mUrl;

    public EarthquakeLoader(Context context,String url) {
        super(context);
        mUrl=url;
    }

    @Override
    public List<Earthquake> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<Earthquake> earthquakes=EarthquakeUtils.fetchEarthquakeData(mUrl);
    return earthquakes;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
