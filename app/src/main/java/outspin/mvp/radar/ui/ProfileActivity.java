package outspin.mvp.radar.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import outspin.mvp.radar.R;
import outspin.mvp.radar.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding activityProfileBinding;
    private EditText etName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityProfileBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(activityProfileBinding.getRoot());

        activityProfileBinding.profilePhoto.profileThumbnailIconInteraction.setImageResource(R.drawable.camera_icon);

        etName = activityProfileBinding.etName;
        etName.setTag(etName.getKeyListener());
        etName.setKeyListener(null);
        activityProfileBinding.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etName.setKeyListener((KeyListener) etName.getTag());
                etName.setBackgroundTintList(ContextCompat.getColorStateList(ProfileActivity.this, R.color.white));
            }
        });

        activityProfileBinding.ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}