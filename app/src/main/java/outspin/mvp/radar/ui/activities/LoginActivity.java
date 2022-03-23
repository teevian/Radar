package outspin.mvp.radar.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import outspin.mvp.radar.R;
import outspin.mvp.radar.api.APIHandler;
import outspin.mvp.radar.api.JSONParser;
import outspin.mvp.radar.api.SignUp;
import outspin.mvp.radar.databinding.ActivityLoginBinding;
import outspin.mvp.radar.models.User;
import outspin.mvp.radar.ui.radar_outside.RadarOutsideFragment;

public class LoginActivity extends AppCompatActivity implements APIHandler.APICallBack {
    ActivityLoginBinding loginBinding;

    private final int LOGIN = 0;
    private final int SIGNUP = 1;
    private int signStatus = 0;

    private String password;
    private User newUser;

    boolean isValidated(String phoneNumber, String password, String name){
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
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);

        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        loginBinding.etName.setVisibility(View.INVISIBLE);

        //Spinner dropdown = loginBinding.spinnerCountryCode;
        String[] countryCodes = new String[]{"+351"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, countryCodes);
        //dropdown.setAdapter(adapter);

        loginBinding.btSubmit.setOnClickListener(view -> {
            String password     = "wihwihswiswi1";//loginBinding.etLoginPassword.getText().toString();
            String phoneNumber  = loginBinding.etLoginPhone.getText().toString();
            //String countryCode  = loginBinding.spinnerCountryCode.getSelectedItem().toString();

            switch (signStatus) {
                case LOGIN:
                    if(isValidated(phoneNumber, password)) {
                        // TODO
                    }
                    break;
                case SIGNUP:
                    String name = loginBinding.etName.getText().toString();
                    if(isValidated(phoneNumber, password, name)) {

                        newUser = new User(-1, name, phoneNumber, "+351", "");
                        APIHandler.QueryAPI queryAPI = new APIHandler.QueryAPI(this);
                        queryAPI.execute();
                    }
                    break;
            }
        });

        loginBinding.tvSign.setOnClickListener(view -> {
            switch (signStatus) {
                case LOGIN:
                    loginBinding.etName.setVisibility(View.VISIBLE);
                    loginBinding.tvMember.setText(getString(R.string.member));
                    loginBinding.tvSign.setText(getString(R.string.login));
                    signStatus = SIGNUP;
                    break;
                case SIGNUP:
                    loginBinding.etName.setVisibility(View.INVISIBLE);
                    loginBinding.tvMember.setText(getString(R.string.not_member));
                    loginBinding.tvSign.setText(getString(R.string.signup));
                    signStatus = LOGIN;
                    break;
            }
        });

    }

    @Override
    public void complete(JSONObject json) {
        // if json is ok and auth is ok
        /*
        Intent intent = new Intent(LoginActivity.this, RadarOutsideFragment.class);
        startActivity(intent);
        */
    }

    @Override
    public APIHandler.APIConnectionBundle getAPIConnectionBundle() {
        JSONObject json = null;
        try {
            json = JSONParser.getAPIJSONTemplate();
            JSONObject userJson = JSONParser.JSONFromUser(newUser);
            userJson.put("password", password);
            json.put("data", userJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new APIHandler.APIConnectionBundle("POST", new String[]{"users", "register"}, new HashMap<>(), json);
    }
}