package outspin.mvp.radar.models;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserThumb {
    private long id;

    private String username;
    private String phoneNumber;
    private String photoURL;
    private String interaction;

    public UserThumb(String username, String phoneNumber, String photoURL) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.photoURL = photoURL;
    }

    public UserThumb(JSONObject jsonUser) {
        try {
            JSONObject data = jsonUser.getJSONObject("data");

            this.id = data.getLong("id");
            this.username = data.getString("name");

            JSONObject thumbs = data.getJSONObject("thumbnails");
            this.photoURL = thumbs.getString("m");
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
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

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
