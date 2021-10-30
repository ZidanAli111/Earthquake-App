package android.example.earthquakerecapapp;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {


    private static final String LOCATION_SEPARATOR = "of";
    TextView magnitudeView;
    TextView primaryLocationView;
    TextView locationOffsetView;
    TextView timeView;
    TextView dateView;


    public EarthquakeAdapter(Context context, List<Earthquake> objects) {
        super(context, 0, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list, parent, false);

        }

        Earthquake currentEarthquake = getItem(position);

        String magnitude = formatMagnitude(currentEarthquake.getmMagnitude());
        magnitudeView = listItemView.findViewById(R.id.magnitude);
        magnitudeView.setText(magnitude);

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        int magnitudeColor = getMagnitudeColor(currentEarthquake.getmMagnitude());

        magnitudeCircle.setColor(magnitudeColor);

        String OriginalLocation = currentEarthquake.getmLocation();

        String locationOffset;
        String primaryLocation;

        if (OriginalLocation.contains(LOCATION_SEPARATOR)) {

            String[] locationArray = OriginalLocation.split(LOCATION_SEPARATOR);

            locationOffset = locationArray[0] + " " + LOCATION_SEPARATOR;

            primaryLocation = locationArray[1];

        } else {

            locationOffset = "Near the";
            primaryLocation = OriginalLocation;

        }

        locationOffsetView = listItemView.findViewById(R.id.location_offset);
        primaryLocationView = listItemView.findViewById(R.id.primary_location);

        locationOffsetView.setText(locationOffset);
        primaryLocationView.setText(primaryLocation);


        Date dateObj = new Date(currentEarthquake.getmTime());


        dateView = listItemView.findViewById(R.id.date);
        String date = FormatDate(dateObj);
        dateView.setText(date);


        timeView = listItemView.findViewById(R.id.time);
        String time = FormatTime(dateObj);
        timeView.setText(time);

        return listItemView;
    }

    private int getMagnitudeColor(double getmMagnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(getmMagnitude);
        switch (magnitudeFloor) {
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    private String FormatDate(Date dateObj) {
        SimpleDateFormat sd = new SimpleDateFormat("LLL dd,yyyy");
        return sd.format(dateObj);
    }

    private String FormatTime(Date dateObj) {

        SimpleDateFormat st = new SimpleDateFormat("h :mm a");
        return st.format(dateObj);

    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }
}
