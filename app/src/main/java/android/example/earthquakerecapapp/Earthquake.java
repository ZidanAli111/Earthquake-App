package android.example.earthquakerecapapp;

public class Earthquake {

    private double mMagnitude;

    private String mLocation;

    private long mTime;

    private String mUrl;



    public Earthquake(double mMagnitude, String mLocation, long mTime, String mUrl) {
        this.mMagnitude = mMagnitude;
        this.mLocation = mLocation;
        this.mTime = mTime;
        this.mUrl = mUrl;
    }


    public double getmMagnitude() {
        return mMagnitude;
    }

    public String getmLocation() {
        return mLocation;
    }

    public long getmTime() {
        return mTime;
    }

    public String getmUrl() {
        return mUrl;
    }
}
