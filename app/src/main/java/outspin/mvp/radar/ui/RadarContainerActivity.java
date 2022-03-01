package outspin.mvp.radar.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import outspin.mvp.radar.R;
import outspin.mvp.radar.databinding.ActivityMainBinding;
import outspin.mvp.radar.network.NetworkManager;
import outspin.mvp.radar.ui.radar_inside.MyBottomSheetDialogFragment;
import outspin.mvp.radar.ui.radar_inside.RadarInsideFragment;

public class RadarContainerActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mainBinding.floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialogFragment myBottomSheetDialogFragment =
                        new MyBottomSheetDialogFragment(mainBinding.getRoot().getContext());
                myBottomSheetDialogFragment.show(getSupportFragmentManager(),
                        myBottomSheetDialogFragment.getTag());
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new RadarInsideFragment())
                .addToBackStack("inside")
                .commit();
    }
}
