package outspin.mvp.radar.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import outspin.mvp.radar.R;
import outspin.mvp.radar.api.APIErrorResponse;
import outspin.mvp.radar.api.APIHandler;
import outspin.mvp.radar.api.JSONParser;
import outspin.mvp.radar.databinding.ActivityLoginBinding;
import outspin.mvp.radar.utils.Validator;

// +351
// 912088800
// teste1234

// https://www.mockplus.com/blog/post/sign-up-login-design-practices
public class SignInActivity extends AppCompatActivity implements APIHandler.APIConnectionCallback {
    ActivityLoginBinding loginBinding;

    private boolean passwordIsOK    = false;
    private boolean phoneIsOK       = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        loginBinding.etLoginPhone.requestFocus();
    }

    @Override
    protected void onStart() {
        super.onStart();

        loginBinding.etLoginPhone.addTextChangedListener(new Validator.TextValidator(loginBinding.etLoginPhone) {

            @Override
            public void validate(TextView textView, String phone) {
                phoneIsOK = Validator.matches(phone, Validator.VALIDATION_KEY.PHONE);
                submitButtonStateChange();
            }

        });

        loginBinding.etLoginPassword.addTextChangedListener(new Validator.TextValidator(loginBinding.etLoginPassword) {

            @Override
            public void validate(TextView textView, String password) {
                passwordIsOK = Validator.matches(password, Validator.VALIDATION_KEY.PASSWORD);
                submitButtonStateChange();
            }
        });

        loginBinding.btSubmit.setOnClickListener(view -> {
            if(canSubmit()) {
                APIHandler.QueryAPI loginTask = new APIHandler.QueryAPI(this);
                loginTask.execute();
            }
        });
    }

    @Override
    public void onSuccess(JSONObject jsonResponse) {
        // TODO create user from JSON and pass it to intent
        Toast.makeText(this, "LOGIN ACCEPTED!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure(APIErrorResponse error) {
        // TODO deal with error
        Toast.makeText(this, "LOGIN REJECTED!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public APIHandler.APIConnectionBundle getAPIConnectionBundle() {
        String countryCode = loginBinding.ccpCountryCode.getSelectedCountryCode();
        String phone       = Objects.requireNonNull(loginBinding.etLoginPhone.getText()).toString();
        String password    = Objects.requireNonNull(loginBinding.etLoginPassword.getText()).toString();

        JSONObject loginJSON = null;
        URL url = null;

        try {
            loginJSON = JSONParser.loginJSONFromCredentials(countryCode, phone, password);
            url = new URL(APIHandler.API_HOSTNAME + "/users/login");
        } catch (JSONException | MalformedURLException e) {
            e.printStackTrace();
        }

        return new APIHandler.APIConnectionBundle("POST", url, loginJSON);
    }

    private boolean canSubmit() { return phoneIsOK && passwordIsOK; }

    private void submitButtonStateChange() {
        loginBinding.btSubmit.setBackgroundTintList(getBaseContext().getColorStateList(
                canSubmit() ? R.color.green : R.color.black
        ));
    }
}