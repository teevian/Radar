package outspin.mvp.radar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import outspin.mvp.radar.R;
import outspin.mvp.radar.data.Macros;
import outspin.mvp.radar.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding loginBinding;

    private final int LOGIN = 0;
    private final int SIGNUP = 1;
    private int signStatus = 0;

    boolean isValidated(String phoneNumber, String password){
        if(phoneNumber.length() != 9) {
            Toast.makeText(LoginActivity.this,
                    "your phone number is invalid.", Toast.LENGTH_SHORT).show();
            return false;
        }

        int passwordLength = password.length();
        if(passwordLength < 9 || passwordLength > 20) {
            Toast.makeText(LoginActivity.this,
                    "your password is invalid.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        loginBinding.btSubmit.setOnClickListener(view -> {
            String password = loginBinding.etLoginPassword.getText().toString();
            String phoneNumber = loginBinding.etLoginPhone.getText().toString();

            if(isValidated(phoneNumber, password)){


                /*
                SharedPreferences sharedPref = getApplication().getSharedPreferences(
                        Macros.PREFERENCE_FILE_AUTHENTICATION, Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(Macros.PREFERENCE_PASSWORD_KEY, password);
                editor.putString(Macros.PREFERENCE_PHONE_NUMBER_KEY, phoneNumber);
                editor.apply();

                Intent intent =  new Intent(LoginActivity.this, RadarNavigationActivity.class);
                startActivity(intent);
                */
            }
        });

        loginBinding.tvSign.setOnClickListener(view -> {
            switch (signStatus) {
                case LOGIN:
                    loginBinding.tvMember.setText(getString(R.string.member));
                    loginBinding.tvSign.setText(getString(R.string.login));
                    signStatus = SIGNUP;
                    break;
                case SIGNUP:
                    loginBinding.tvMember.setText(getString(R.string.not_member));
                    loginBinding.tvSign.setText(getString(R.string.signup));
                    signStatus = LOGIN;
                    break;
            }
        });

    }
}