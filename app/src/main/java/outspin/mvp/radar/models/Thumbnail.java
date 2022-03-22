package outspin.mvp.radar.models;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import outspin.mvp.radar.api.JSONParser;

public class Thumbnail {
    private long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String photoURL;
    private String interaction;

    public Thumbnail(long id, String firstName, String lastName, String phoneNumber, String photoURL) {
        this.id = id;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.lastName = lastName;
        this.photoURL = photoURL;
    }

    /**
     * Constructs a user from JSON. Sets name to "" if name is empty.
     *
     * @param userJSON json of a user thumbnail
     */
    public Thumbnail(@NonNull JSONObject userJSON) throws JSONException {
        this.id         = userJSON.getLong("id");
        this.photoURL   = userJSON.getString("photo_url");
        this.interaction = userJSON.getString("interaction");

        // TODO improve this
        this.firstName  = userJSON.has("first_name") ? userJSON.getString("first_name") : "";
        this.lastName   = userJSON.has("last_name") ? userJSON.getString("last_name") : "";
    }

    @NonNull
    @Override
    public String toString() {
        return "{ \"id\" :" + this.id + ", \"thumbnail\":" + this.photoURL + ", \"interaction\":" + this.interaction +"}";
    }

    /*  getters && setters   */
    public String getFirstName() { return this.firstName; }
    public void setFirstName(String username) { this.firstName = username; }

    public String getLastName() { return this.firstName; }
    public void setLastName(String username) { this.firstName = username; }

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
