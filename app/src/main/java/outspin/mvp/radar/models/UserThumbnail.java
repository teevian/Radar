package outspin.mvp.radar.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import outspin.mvp.radar.api.JSONBuilder;

public class UserThumbnail {
    private long id;
    private String username;
    private String phonenumber; // ID
    private String photoURL;
    private String interaction;

    public UserThumbnail(String username, String phonenumber, String photoURL) {
        this.username = username;
        this.phonenumber = phonenumber;
        this.photoURL = photoURL;
    }

    public UserThumbnail(JSONObject jsonUser) {
        try {
            this.photoURL = jsonUser.getString("thumbnail");
            this.id = jsonUser.getLong("id");
            this.interaction = jsonUser.getString("interaction");
            Log.d("INTERACTION_IS: ", this.interaction);
        } catch (Exception ignored) {}
    }

    @Override
    public String toString() {
        return "UserThumbnail{" + this.id + ", " + this.interaction +"}";
    }

    /*  getters && setters   */
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPhonenumber() { return phonenumber; }
    public void setPhonenumber(String phonenumber) { this.phonenumber = phonenumber; }

    public String getPhotoURL() { return photoURL; }
    public void setPhotoURL(String photoURL) { this.photoURL = photoURL; }

    public String getInteraction() {
        return this.interaction;
    }

    public void setInteraction(String interaction) {
        this.interaction = interaction;
    }
}
