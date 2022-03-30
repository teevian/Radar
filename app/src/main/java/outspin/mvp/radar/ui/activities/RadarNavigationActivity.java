package outspin.mvp.radar.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import outspin.mvp.radar.R;
import outspin.mvp.radar.databinding.ActivityRadarNavigationBinding;
import outspin.mvp.radar.ui.adapters.InteractionsAdapter;
import outspin.mvp.radar.ui.dialogs.InteractionsDialog;
import outspin.mvp.radar.ui.fragments.RadarInsideFragment;

public class RadarNavigationActivity extends AppCompatActivity
        implements InteractionsAdapter.InteractionClickListener {
    ActivityRadarNavigationBinding mainBinding;

    private FusedLocationProviderClient fusedLocationProviderClient;

    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);

        mainBinding = ActivityRadarNavigationBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        mainBinding.floatingButton.getRoot().setOnClickListener(view -> {
            BottomSheetDialogFragment interactionsDialog = new InteractionsDialog(this);
            interactionsDialog.show(getSupportFragmentManager(), interactionsDialog.getTag());
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new RadarInsideFragment())
                .addToBackStack("outside")
                .commit();
    }

    @Override
    public void onItemClick(View view, int position, Context parent) {
        BottomSheetDialogFragment interactionsDialogFragment = new InteractionsDialog(this);
        interactionsDialogFragment.show(getSupportFragmentManager(), interactionsDialogFragment.getTag());
    }

    public void locationButton() {
        if (ActivityCompat.checkSelfPermission(RadarNavigationActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // when permission granted
            getLocation();
        } else {
            ActivityCompat.requestPermissions(RadarNavigationActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    public Location getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                // initialize location
                Location location = task.getResult();
                if (location != null) {
                    try {
                        // initialize Geocoder
                        Geocoder geocoder = new Geocoder(RadarNavigationActivity.this,
                                Locale.getDefault());

                        // initialize address list
                        List<Address> addressList = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
                        // print latitude & longitude
                        String latitude = String.valueOf(addressList.get(0).getLatitude());
                        String longitude = String.valueOf(addressList.get(0).getLongitude());

                        Log.d("KKKKKK: ", latitude);
                        Log.d("KKKKKK: ", longitude);
                        Log.d("KKKKKK: ", addressList.get(0).getLocality());
                        Log.d("KKKKKK: ", addressList.get(0).getAddressLine(0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    LocationRequest mLocationRequest = LocationRequest.create();
                    mLocationRequest.setInterval(60000);
                    mLocationRequest.setFastestInterval(5000);
                    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationCallback mLocationCallback = new LocationCallback() {
                        @Override
                        public void onLocationResult(@NonNull LocationResult locationResult) {
                            for (Location location : locationResult.getLocations()) {
                                //TODO: UI updates.
                            }
                        }
                    };
                    if (ActivityCompat.checkSelfPermission(RadarNavigationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(RadarNavigationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    LocationServices.getFusedLocationProviderClient(getBaseContext()).requestLocationUpdates(mLocationRequest, mLocationCallback, null);
                }
            }
        });

        LocationServices.getFusedLocationProviderClient(getBaseContext()).getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                //TODO: UI updates.
            }
        });

        return null;
    }

}
