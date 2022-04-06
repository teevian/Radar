package outspin.mvp.radar.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

// https://www.mockplus.com/blog/post/sign-up-login-design-practices
public class LoginActivity extends AppCompatActivity implements APIHandler.APIConnectionCallback {
    ActivityLoginBinding loginBinding;

    private String phone;
    private String password;

    private boolean passwordIsOK    = false;
    private boolean phoneIsOK       = false;
    private boolean nameIsOk        = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();

        loginBinding.etName.setVisibility(View.INVISIBLE);

        //  set listeners
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

        // TODO validate name input
        loginBinding.etName.addTextChangedListener(new Validator.TextValidator(loginBinding.etName) {

            @Override
            public void validate(TextView textView, String name) {
                if(name.matches(Objects.requireNonNull(Validator.VALIDATION_SCHEMES.get(Validator.VALIDATION_KEY.NAME)))) {

                }
            }
        });

        loginBinding.btSubmit.setOnClickListener(view -> {
            phone = Objects.requireNonNull(loginBinding.etLoginPhone.getText()).toString();
            password = Objects.requireNonNull(loginBinding.etLoginPassword.getText()).toString();

            if(canSubmit()) {
                APIHandler.QueryAPI loginTask = new APIHandler.QueryAPI(this);
                loginTask.execute();
            }
        });
    }

    @Override
    public void onSuccess(JSONObject jsonResponse) {
        Intent openLoggedApp = new Intent(LoginActivity.this, RadarNavigationActivity.class);

        // TODO create user from JSON and pass it to intent

        openLoggedApp.putExtra("id", 3);
        startActivity(openLoggedApp);
    }

    @Override
    public void onFailure(APIErrorResponse error) {
        // TODO deal with error
    }

    @Override
    public APIHandler.APIConnectionBundle getAPIConnectionBundle() {
        // TODO check if it is register or login

        JSONObject loginJSON = null;
        URL url = null;
        try {
            if(phone.isEmpty() || password.isEmpty()) { phone = "912033800";password = "operdade2"; }
            //if(phone.isEmpty() || password.isEmpty()) { phone = "912088808";password = "operdade2"; }

            loginJSON = JSONParser.loginJSONFromCredentials(phone, password);
            url = new URL(APIHandler.API_HOSTNAME + "/users/login");
        } catch (JSONException | MalformedURLException e) {
            e.printStackTrace();
        }

        /** this is test */
        return new APIHandler.APIConnectionBundle("POST", url, loginJSON);
    }

    private boolean canSubmit() {
        return true;//phoneIsOK && passwordIsOK;
    }

    private void submitButtonStateChange() {
        loginBinding.btSubmit.setBackgroundTintList(getBaseContext().getColorStateList(
                canSubmit() ? R.color.green : R.color.black
        ));
    }
}

/*
users/login

meta :
    {
        apiVersion : "0.1"
    }
data :
    {
        kind : "login"
        list:
        [
         {
            "phone" : "",
            "password" : "" }
        ]
    }
 */

/*
users/register

meta :
    {
        apiVersion : "0.1"
    }
data :
    {
        kind : "register"
        list:
        [
         {
            "phone" : "",
            "countryCode" : "",
            "password" : "",
            "firstName" :
            "lastName" : }
        ]
    }
 */