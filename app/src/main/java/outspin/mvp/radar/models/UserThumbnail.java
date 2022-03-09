package outspin.mvp.radar.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import outspin.mvp.radar.utils.JSONBuilder;

public class UserThumbnail {
    private String username;
    private String phonenumber; // ID
    private String photoURL;

    public UserThumbnail(String username, String phonenumber, String photoURL) {
        this.username = username;
        this.phonenumber = phonenumber;
        this.photoURL = photoURL;
    }

    public UserThumbnail(JSONObject jsonUser) {
        try {
           UserThumbnail temp = JSONBuilder.UserFromJSON(jsonUser);
           this.username = temp.username;
           this.phonenumber = temp.phonenumber;
           this.photoURL = temp.phonenumber;

        } catch (JSONException e) {
            Log.d("ERROR: ", e.toString());
        }
    }

    @Override
    public String toString() {
        return "UserThumbnail{" +
                "username='" + username + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", photoURL='" + photoURL + '\'' +
                '}';
    }

    /*  getters && setters   */
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPhonenumber() { return phonenumber; }
    public void setPhonenumber(String phonenumber) { this.phonenumber = phonenumber; }

    public String getPhotoURL() { return photoURL; }
    public void setPhotoURL(String photoURL) { this.photoURL = photoURL; }
}
