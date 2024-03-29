package outspin.mvp.radar.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import outspin.mvp.radar.R;
import outspin.mvp.radar.api.APIErrorResponse;
import outspin.mvp.radar.api.APIHandler;
import outspin.mvp.radar.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity implements APIHandler.APIConnectionCallback {
    private ActivityProfileBinding activityProfileBinding;
    private EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        androidx.core.splashscreen.SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

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
    public void onSuccess(JSONObject json) {
        Log.d("API:::::::::::::", "Success");
    }

    @Override
    public void onFailure(APIErrorResponse jsonError) {

    }

    @Override
    public APIHandler.APIConnectionBundle getAPIConnectionBundle() {
        URL url = null;
        try {
            url = new URL(APIHandler.API_HOSTNAME + "/club?id=7");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return new APIHandler.APIConnectionBundle("GET", url, null);
    }
}