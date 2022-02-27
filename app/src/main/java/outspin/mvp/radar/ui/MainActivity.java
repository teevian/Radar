package outspin.mvp.radar.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import outspin.mvp.radar.R;
import outspin.mvp.radar.databinding.ActivityMainBinding;
import outspin.mvp.radar.databinding.FragmentRadarInsideBinding;

/* TODO(2) mudar nome de classse / layout */
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new RadarInsideFragment())
                .addToBackStack("inside")
                .commit();
    }
}
