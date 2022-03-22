package outspin.mvp.radar.models;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class Interaction {
    private String message;
    private long interactorID;
    private long interactedID;
    private String photoURI;
    private int type;

    public Interaction(@NonNull JSONObject interactionJSON) throws JSONException {
            this.interactedID = interactionJSON.getLong("interacted_id");
            this.interactorID = interactionJSON.getLong("interactor_id");
            this.type = interactionJSON.getInt("type");
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
