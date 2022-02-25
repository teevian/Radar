package outspin.mvp.radar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import outspin.mvp.radar.R;
import outspin.mvp.radar.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());
    }
}