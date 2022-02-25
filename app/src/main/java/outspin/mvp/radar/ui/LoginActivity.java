package outspin.mvp.radar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import outspin.mvp.radar.R;
import outspin.mvp.radar.data.Macros;
import outspin.mvp.radar.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        loginBinding.btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = loginBinding.etLoginPassword.getText().toString();
                String phoneNumber = loginBinding.etLoginPhone.getText().toString();

                SharedPreferences sharedPref = getApplication().getSharedPreferences(
                        Macros.PREFERENCE_FILE_AUTHENTICATION, Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(Macros.PREFERENCE_PASSWORD_KEY, password);
                editor.putString(Macros.PREFERENCE_PHONE_NUMBER_KEY, phoneNumber);
                editor.apply();

                Intent intent =  new Intent(LoginActivity.this, LaunchScreenActivity.class);
                startActivity(intent);
            }
        });


    }
}