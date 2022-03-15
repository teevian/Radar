package outspin.mvp.radar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import outspin.mvp.radar.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding activityProfileBinding;
    private EditText etEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityProfileBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(activityProfileBinding.getRoot());

        etEdit.setTag(etEdit.getKeyListener());
        activityProfileBinding.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etEdit.setTag(etEdit.getKeyListener());
            }
        });

        activityProfileBinding.ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}