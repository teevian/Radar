package outspin.mvp.radar.models;

public class User {
    private long id;
    private String name;
    private String phoneNumber;
    private long clubID;
    private String photoURL;
    private String countryCode;

    public User(long id, String name, String phoneNumber, String countryCode, String photoURL) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.photoURL = photoURL;
        this.countryCode = countryCode;
    }

    /*  Getters && Setters  */
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public long getClubID() {
        return clubID;
    }
    public void setClubID(long clubID) {
        this.clubID = clubID;
    }
    public String getPhotoURL() {
        return photoURL;
    }
    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
