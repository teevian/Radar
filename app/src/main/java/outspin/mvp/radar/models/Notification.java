package outspin.mvp.radar.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import outspin.mvp.radar.api.JSONBuilder;

public class Notification {
    public static int WAVE = 0;
    public static int WAVE_BACK = 1;
    public static int BURN = 2;

    private String message;
    private long senderID;
    private long receiverID;
    private String photoURI;
    private int type;

    public Notification(long receiverID, long senderID, int type) {
        this.receiverID = receiverID;
        this.senderID = senderID;
        this.type = type;
        this.photoURI = "https://turingnotes.com/wp-content/uploads/2022/02/photo18.jpeg";
    }

    public Notification(JSONObject jsonNotification) {
        try {
            this.receiverID = jsonNotification.getInt("receiverID");
            this.senderID = jsonNotification.getInt("senderID");
            this.type = jsonNotification.getInt("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Notification{" +
                ", senderID=" + senderID +
                ", receiverID=" + receiverID +
                ", type=" + type +
                '}';
    }

    /*  getters & setters   */
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public long getSenderID() { return senderID; }
    public void setSenderID(int senderID) { this.senderID = senderID; }

    public String getSenderPhotoURI() { return photoURI; }
    public void setSenderPhotoURI(String senderPhotoURI) { this.photoURI = senderPhotoURI; }
}
