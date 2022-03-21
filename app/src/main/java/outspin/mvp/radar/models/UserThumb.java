package outspin.mvp.radar.models;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserThumb {
    private long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String photoURL;
    private String interaction;

    public UserThumb(String firstName, String phoneNumber, String photoURL) {
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.photoURL = photoURL;
    }

    public UserThumb(JSONObject jsonUser) {
        try {
            JSONObject data = jsonUser.has("data") ? jsonUser.getJSONObject("data") : jsonUser;

            this.id = data.getLong("id");
            this.firstName = data.getString("first_name");
            this.lastName = data.getString("last_name");
            this.photoURL = data.getString("photo_url");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "{ \"id\" :" + this.id + ", \"thumbnail\":" + this.photoURL + ", \"interaction\":" + this.interaction +"}";
    }

    /*  getters && setters   */
    public String getFirstName() { return this.firstName; }
    public void setFirstName(String username) { this.firstName = username; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPhotoURL() { return photoURL; }
    public void setPhotoURL(String photoURL) { this.photoURL = photoURL; }

    public String getInteraction() {
        return this.interaction;
    }
    public void setInteraction(String interaction) {
        this.interaction = interaction;
    }
}
