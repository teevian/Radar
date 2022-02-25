package outspin.mvp.radar.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class LaunchScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String sharedPrefsFile = "outspin.mvp.radar.PREFERENCE_FILE_TOKEN";
        SharedPreferences sharedPref = this.getSharedPreferences(
                sharedPrefsFile, Context.MODE_PRIVATE);

        String password = "";
        String phoneNumber = "";
        String validatedPassword = sharedPref.getString("password", password);
        String validatedPhoneNumber = sharedPref.getString("phoneNumber", phoneNumber);

        if(validatedPassword == "" || validatedPhoneNumber == "") {
            Intent launchIntent = new Intent(this, LoginActivity.class);
            startActivity(launchIntent);
            finish();
        } else{

        }


    }
}
