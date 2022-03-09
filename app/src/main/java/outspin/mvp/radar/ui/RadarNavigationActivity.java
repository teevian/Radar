package outspin.mvp.radar.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import outspin.mvp.radar.R;
import outspin.mvp.radar.databinding.ActivityRadarNavigationBinding;
import outspin.mvp.radar.ui.radar_inside.RadarInsideFragment;
import outspin.mvp.radar.ui.radar_outside.RadarOutsideFragment;

public class RadarNavigationActivity extends AppCompatActivity
    implements InteractionsAdapter.InteractionClickListener {
    ActivityRadarNavigationBinding mainBinding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = ActivityRadarNavigationBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mainBinding.floatingButton.getRoot().setOnClickListener(view -> {
            BottomSheetDialogFragment myBottomSheetDialogFragment = new NotificationsDialog(this);
            myBottomSheetDialogFragment.show(getSupportFragmentManager(), myBottomSheetDialogFragment.getTag());
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new RadarInsideFragment())
                .addToBackStack("inside")
                .commit();
    }

    @Override
    public void onItemClick(View view, int position, Context parent) {
        Toast.makeText(this, "lala", Toast.LENGTH_SHORT).show();
        BottomSheetDialogFragment myBottomSheetDialogFragment = new NotificationsDialog(this);
        myBottomSheetDialogFragment.show(getSupportFragmentManager(), myBottomSheetDialogFragment.getTag());
    }
}
