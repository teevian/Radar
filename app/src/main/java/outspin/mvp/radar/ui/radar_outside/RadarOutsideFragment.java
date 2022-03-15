package outspin.mvp.radar.ui.radar_outside;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import outspin.mvp.radar.databinding.ClubLayoutBinding;
import outspin.mvp.radar.databinding.FragmentRadarInsideBinding;
import outspin.mvp.radar.databinding.FragmentRadarOutsideBinding;
import outspin.mvp.radar.ui.RadarNavigationActivity;

public class RadarOutsideFragment extends Fragment {
    private FragmentRadarOutsideBinding binding;
    private static final List<double[]> positions = Arrays.asList(
            new double[]{0.1269, 0.0993},
            new double[]{0.5856, 0.1420},
            new double[]{0.2289,0.2791},
            new double[]{0.6495, 0.4253},
            new double[]{0.5235, 0.5920},
            new double[]{0.0728, 0.5571},
            new double[]{0.5111, 0.5924},
            new double[]{0.1899,0.7845}
    );
/*
    private FusedLocationProviderClient fusedLocationProviderClient;
    private ActivityResultLauncher<String[]> activityResultLauncher; // TODO dd
    private String[] locationPermissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION};
*/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRadarOutsideBinding.inflate(inflater, container, false);
        RelativeLayout rl = binding.container;

        binding.btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RadarNavigationActivity)getActivity()).locationButton();
            }
        });

        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        double pxHeight = displayMetrics.heightPixels;
        double pxWidth = displayMetrics.widthPixels;


        double dp1 = (double) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1,
                getContext().getResources().getDisplayMetrics());
        int width = (int) (115*dp1);
        int height = (int) (100*dp1);

        for (int i = 0; i < 8; ++i) {
            ClubLayoutBinding clubBinding = ClubLayoutBinding.inflate( (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE), container, false);
            View clubIcon = clubBinding.getRoot();

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);

            params.leftMargin = (int) (positions.get(i)[0]*pxWidth);
            params.topMargin = (int) (positions.get(i)[1]*pxHeight);
            rl.addView(clubIcon, params);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
/*
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                new ActivityResultCallback<Map<String, Boolean>>() {

                    @Override
                    public void onActivityResult(Map<String, Boolean> result) {
                        Log.e("activityResultLauncher", "" + result.toString());

                        boolean areAllGranted = true;
                        for (Boolean b : result.values()) {
                            areAllGranted = areAllGranted && b;
                        }

                        if (areAllGranted) {
                            getLocation();
                        }
                    }
        });

        binding.btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityResultLauncher.launch(locationPermissions);
            }
        });*/
    }


}
