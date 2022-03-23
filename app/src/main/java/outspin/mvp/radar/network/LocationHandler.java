package outspin.mvp.radar.network;


import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.List;

public class LocationHandler extends Service implements LocationListener {
    boolean isGPSEnabled = true;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;

    Location location;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private static final long MIN_TIME_BW_UPDATES = 60000; // 1 minute

    protected LocationManager locationManager;
    private final Context context;
    private LocationHandler instance;

    double latitude, longitude;

    public LocationHandler(Context context) {
        this.context = context;
        LocationHandler.getLocationPermissions(context);
        locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        location = getLastKnownLocation();
        getLocation();
    }

    public String countryCode() {
        String locale = context.getResources().getConfiguration().locale.getCountry();
        //TelephonyManager tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        //String countryCodeValue = tm.getNetworkCountryIso();

        return locale;
    }


    private Location getLastKnownLocation() {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            @SuppressLint("MissingPermission") Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    public static void getLocationPermissions(Context parent) {
        int REQUEST_LOCATION = 44;
        if (ActivityCompat.checkSelfPermission(parent, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(parent, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) parent, new String[]{ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
    }

    // CAREFULLY TODO
    @SuppressLint("MissingPermission")
    public Location getLocation() {

        Log.d("LALALALALALALA", "GET LOCATION");
        // create location service instance
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(isNetworkEnabled) {

            // check permissions
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
            }

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, (android.location.LocationListener) this);
            Log.d("Network", "Network");

            //location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location == null)
                Log.d("LALALALALALALA", "LOCATION IS NULL");


            if(location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Log.d("LALALALALALALALA NETWORK", String.valueOf(latitude));
                Log.d("LALALALALALALALA NETQOWKR", String.valueOf(longitude));

            }
        } else if(isGPSEnabled) {
            if(location == null) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                }

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, (android.location.LocationListener) this);
                Log.d("GPS Enabled", "GPS Enabled");

                //location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    Log.d("LALALALALALALALA GPS", String.valueOf(latitude));
                    Log.d("LALALALALALALALA GPS", String.valueOf(longitude));
                }
            }
        } else {
            Log.d("LALALALALALALA", "NO SERVICE :(");
        }

        return null;
    }

    public void stopUsingGPS() {
        if(locationManager != null){
            locationManager.removeUpdates(LocationHandler.this);
        }
    }

    public double getLatitude() {
        if(this.location != null) {
            this.latitude = this.location.getLatitude();
        }

        return this.latitude;
    }

    public double getLongitude() {
        if(this.location != null) {
            this.longitude = this.location.getLongitude();
        }

        return this.longitude;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
