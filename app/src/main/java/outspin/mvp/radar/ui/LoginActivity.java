package outspin.mvp.radar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import outspin.mvp.radar.R;
import outspin.mvp.radar.api.APIHandler;
import outspin.mvp.radar.api.SignUp;
import outspin.mvp.radar.databinding.ActivityLoginBinding;
import outspin.mvp.radar.models.UserNotInUse;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding loginBinding;

    private final int LOGIN = 0;
    private final int SIGNUP = 1;
    private int signStatus = 0;

    boolean isValidated(String phoneNumber, String password, String passwordDuplicate, String name){
        if(phoneNumber.length() != 9) {
            Toast.makeText(LoginActivity.this,
                    "your phone number is invalid.", Toast.LENGTH_SHORT).show();
            return false;
        }

        int passwordLength = password.length();
        if(passwordLength < 9 || passwordLength > 20 || !password.equals(passwordDuplicate)) {
            Toast.makeText(LoginActivity.this,
                    "your password is invalid.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return name.length() != 0;
    }

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

        loginBinding.etName.setVisibility(View.GONE);
        loginBinding.etLoginPasswordDuplicate.setVisibility(View.GONE);

        Spinner dropdown = loginBinding.spinnerCountryCode;
        String[] items = new String[]{"+351"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, items);
        dropdown.setAdapter(adapter);

        loginBinding.btSubmit.setOnClickListener(view -> {
            String password = loginBinding.etLoginPassword.getText().toString();
            String phoneNumber = loginBinding.etLoginPhone.getText().toString();
            String countryCode = loginBinding.spinnerCountryCode.getSelectedItem().toString();

            switch (signStatus) {
                case LOGIN:
                    if(isValidated(phoneNumber, password)) {
                        // TODO
                    }
                    break;
                case SIGNUP:
                    String name = loginBinding.etName.getText().toString();
                    String passwordDuplicate = loginBinding.etLoginPasswordDuplicate.getText().toString();
                    if(isValidated(phoneNumber, password, passwordDuplicate, name)) {
                        SignUp signUp = new SignUp(new UserNotInUse(-1, name, phoneNumber, countryCode, ""), password);
                        APIHandler.APIConnectionBundle bundle = signUp.getAPIConnectionBundle();
                        APIHandler.QueryAPI queryAPI = new APIHandler.QueryAPI(signUp);
                        queryAPI.execute();
                    }
                    break;
            }

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
        });

        loginBinding.tvSign.setOnClickListener(view -> {
            switch (signStatus) {
                case LOGIN:
                    loginBinding.etName.setVisibility(View.VISIBLE);
                    loginBinding.etLoginPasswordDuplicate.setVisibility(View.VISIBLE);
                    loginBinding.tvMember.setText(getString(R.string.member));
                    loginBinding.tvSign.setText(getString(R.string.login));
                    signStatus = SIGNUP;
                    break;
                case SIGNUP:
                    loginBinding.etName.setVisibility(View.GONE);
                    loginBinding.etLoginPasswordDuplicate.setVisibility(View.GONE);
                    loginBinding.tvMember.setText(getString(R.string.not_member));
                    loginBinding.tvSign.setText(getString(R.string.signup));
                    signStatus = LOGIN;
                    break;
            }
        });

    }
}