package outspin.mvp.radar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Toast;

import outspin.mvp.radar.R;
import outspin.mvp.radar.data.Macros;
import outspin.mvp.radar.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding loginBinding;

    boolean isPhoneValid(String phoneNumber) {
        return PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber);
    }

    boolean isPasswordValid(String password){
        int passwordLength = password.length();
        if(passwordLength > 8 && passwordLength < 21)
            return true;
        return false;
    }

    boolean isLoginValidated(String phoneNumber, String password){
        if(phoneNumber.length() != 9) {
            Toast.makeText(LoginActivity.this,
                    "your phone number is invalid.", Toast.LENGTH_LONG).show();
            return false;
        }

        int passwordLength = password.length();
        if(passwordLength < 9 || passwordLength > 20) {
            Toast.makeText(LoginActivity.this,
                    "your password is invalid.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

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

                if(isLoginValidated(phoneNumber, password)){
                    SharedPreferences sharedPref = getApplication().getSharedPreferences(
                            Macros.PREFERENCE_FILE_AUTHENTICATION, Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(Macros.PREFERENCE_PASSWORD_KEY, password);
                    editor.putString(Macros.PREFERENCE_PHONE_NUMBER_KEY, phoneNumber);
                    editor.apply();

                    Intent intent =  new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        loginBinding.tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
}