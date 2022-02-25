package outspin.mvp.radar.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import outspin.mvp.radar.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding signUpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(signUpBinding.getRoot());


    }

}
