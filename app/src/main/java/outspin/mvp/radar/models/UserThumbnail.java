package outspin.mvp.radar.models;

public class UserThumbnail {
    private String username;
    private String phonenumber; // ID
    private String photoURL;

    public UserThumbnail(String username, String phonenumber, String photoURL) {
        this.username = username;
        this.phonenumber = phonenumber;
        this.photoURL = photoURL;
    }

    /*  getters && setters   */
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPhonenumber() { return phonenumber; }
    public void setPhonenumber(String phonenumber) { this.phonenumber = phonenumber; }

    public String getPhotoURL() { return photoURL; }
    public void setPhotoURL(String photoURL) { this.photoURL = photoURL; }
}
