package outspin.mvp.radar.api;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.ULocale;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.Date;

import outspin.mvp.radar.data.Macros;

public class Token {
    private String TOKEN_API_KEY;
    private String dateCreated;
    private String dateExpires;
    private Context context;
    private JSONObject json;

    public Token(Context context, JSONObject json) {
        String dateCreatedString, dateExpiresString;

        try {
            this.TOKEN_API_KEY = json.getString("token");
            //this.userID = json.getLong("userID");

            dateCreatedString = json.getString("dateCreated");
            dateExpiresString = json.getString("dateExpires");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDateTime dateCreated = LocalDateTime.parse(json.getString("dateCreated")); // "2015-02-20T06:30:00"
                LocalDateTime dataExpires = LocalDateTime.parse(json.getString("dateExpires"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void storeInSharedPreferences() {
        SharedPreferences prefs = context.getSharedPreferences("com.outspin.auth", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Macros.PREFERENCES_AUTH_TOKEN, this.TOKEN_API_KEY);
        editor.apply();
    }

    public String getCurrentTokenString() {
        SharedPreferences prefs = context.getSharedPreferences("com.outspin.auth", Activity.MODE_PRIVATE);
        return prefs.getString(Macros.PREFERENCES_AUTH_TOKEN, "NO TOKEN");
    }

    public JSONObject getCurrentTokenObject() {
        JSONObject jsonToken = new JSONObject();
        try {
            jsonToken.put("TOKEN", this.TOKEN_API_KEY);
            jsonToken.put("CREATED", dateCreated);
            jsonToken.put("EXPIRES", this.dateExpires);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonToken;
    }

    public boolean hasTokenExpired() {
        return false;
    }
}
