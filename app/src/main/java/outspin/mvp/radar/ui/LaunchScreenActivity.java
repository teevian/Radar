package outspin.mvp.radar.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import javax.crypto.Mac;

import outspin.mvp.radar.data.Macros;

public class LaunchScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = this.getSharedPreferences(
                Macros.PREFERENCE_FILE_AUTHENTICATION, Context.MODE_PRIVATE);

        String password = "";
        String phoneNumber = "";
        String validatedPassword = sharedPref.getString(Macros.PREFERENCE_PASSWORD_KEY, password);
        String validatedPhoneNumber = sharedPref.getString(Macros.PREFERENCE_PHONE_NUMBER_KEY, phoneNumber);

        if(validatedPassword.equals("") || validatedPhoneNumber.equals("")) {
            Intent launchIntent = new Intent(this, LoginActivity.class);
            startActivity(launchIntent);
            finish();
        } else{
            Intent launchIntent = new Intent(this, LoginActivity.class);
            startActivity(launchIntent);
            finish();
        }


    }
}
