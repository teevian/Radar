package outspin.mvp.radar.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import outspin.mvp.radar.R;
import outspin.mvp.radar.api.APICallBack;
import outspin.mvp.radar.api.APIHandler;
import outspin.mvp.radar.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity implements APICallBack {
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
        activityProfileBinding.ivEdit.setOnClickListener(view -> {
            etName.setKeyListener((KeyListener) etName.getTag());
            etName.setBackgroundTintList(ContextCompat.getColorStateList(ProfileActivity.this, R.color.white));
        });

        activityProfileBinding.ivSettings.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        APIHandler.QueryAPI queryAPI = new APIHandler.QueryAPI(this);
        queryAPI.execute();
    }

    @Override
    public void complete(JSONObject json) {
        Log.d("API:::::::::::::", "Success");
    }

    @Override
    public APIHandler.APIConnectionBundle getAPIConnectionBundle() {
        Map<String, String> queries;
        queries = new HashMap<>();
        queries.put("id", "7");
        return new APIHandler.APIConnectionBundle("GET", new String[]{"club"}, queries, null);
    }
}