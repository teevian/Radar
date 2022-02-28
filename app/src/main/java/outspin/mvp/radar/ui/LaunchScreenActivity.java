package outspin.mvp.radar.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import outspin.mvp.radar.data.Macros;
import outspin.mvp.radar.network.NetworkManager;

public class LaunchScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent launchIntent = new Intent(this, LoginActivity.class);
        startActivity(launchIntent);
        finish();
    }
}
