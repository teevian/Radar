package outspin.mvp.radar.models;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class Interaction {
    public static int WAVE = 0;
    public static int WAVE_BACK = 1;
    public static int BURN = 2;

    private String message;
    private long interactorID;
    private long interactedID;
    private String photoURI;
    private int type;

    public Interaction(long receiverID, long interactorID, int type) {
        this.interactedID = receiverID;
        this.interactorID = interactorID;
        this.type = type;
        this.photoURI = "https://turingnotes.com/wp-content/uploads/2022/02/photo18.jpeg";
    }

    public Interaction(JSONObject jsonInteraction) {
        try {
            JSONObject data = jsonInteraction.has("data") ? jsonInteraction.getJSONObject("data") : jsonInteraction;

            this.interactedID = data.getLong("receiverID");
            this.interactorID = data.getLong("senderID");
            this.type = data.getInt("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    public String toString() {
        return "Notification{" +
                ", senderID=" + interactorID +
                ", receiverID=" + interactedID +
                ", type=" + type +
                '}';
    }

    /*  getters & setters   */
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public long getInteractorID() { return interactorID; }
    public void setInteractorID(int interactorID) { this.interactorID = interactorID; }

    public String getSenderPhotoURI() { return photoURI; }
    public void setSenderPhotoURI(String senderPhotoURI) { this.photoURI = senderPhotoURI; }
}
