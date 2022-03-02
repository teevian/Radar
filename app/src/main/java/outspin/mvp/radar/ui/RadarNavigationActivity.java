package outspin.mvp.radar.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import outspin.mvp.radar.R;
import outspin.mvp.radar.databinding.ActivityRadarNavigationBinding;
import outspin.mvp.radar.ui.radar_inside.MyBottomSheetDialogFragment;
import outspin.mvp.radar.ui.radar_inside.RadarInsideFragment;

public class RadarNavigationActivity extends AppCompatActivity {
    ActivityRadarNavigationBinding mainBinding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = ActivityRadarNavigationBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mainBinding.floatingButton.getRoot().setOnClickListener(view -> {
            BottomSheetDialogFragment myBottomSheetDialogFragment =
                    new MyBottomSheetDialogFragment(mainBinding.getRoot().getContext());
            myBottomSheetDialogFragment.show(getSupportFragmentManager(),
                    myBottomSheetDialogFragment.getTag());
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new RadarInsideFragment())
                .addToBackStack("inside")
                .commit();
    }
}
