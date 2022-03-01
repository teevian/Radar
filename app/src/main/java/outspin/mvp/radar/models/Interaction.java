package outspin.mvp.radar.models;

public class Interaction {
    private String message;
    private int senderID;
    private String senderPhotoURI;

    public Interaction(String message, int senderID, String senderPhotoURI) {
        this.message = message;
        this.senderID = senderID;
        this.senderPhotoURI = senderPhotoURI;
    }

    public Interaction(String message, int senderID) {
        this.message = message;
        this.senderID = senderID;
        // get photo from URI
    }

    /*  getters & setters   */
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public int getSenderID() { return senderID; }
    public void setSenderID(int senderID) { this.senderID = senderID; }

    public String getSenderPhotoURI() { return senderPhotoURI; }
    public void setSenderPhotoURI(String senderPhotoURI) { this.senderPhotoURI = senderPhotoURI; }
}
