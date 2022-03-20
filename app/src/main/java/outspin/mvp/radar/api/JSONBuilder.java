package outspin.mvp.radar.api;

import android.util.Log;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import outspin.mvp.radar.models.Notification;
import outspin.mvp.radar.models.User;
import outspin.mvp.radar.models.UserThumb;

public class JSONBuilder {
    
    @NonNull
    @Contract("_ -> new")
    public static JSONObject JSONfromString(String jsonString) throws JSONException {
        return new JSONObject(jsonString);
    }

    @NonNull
    public static  JSONObject standardJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("apiVersion", "0.1");
        return json;
    }

    public static JSONObject JSONFromUser(User user) throws JSONException {
        JSONObject userJson = new JSONObject();
        String id = String.valueOf(user.getId());
        userJson.put("id", id);
        userJson.put("name", user.getName());
        userJson.put("phoneNumber", user.getPhoneNumber());
        userJson.put("countryCode", user.getCountryCode());
        userJson.put("photoURl", user.getPhotoURL());

        return userJson;
    }

/*
    public static Notification notificationFromJSON(JSONObject notificationJSON) throws JSONException {
        JSONObject data = notificationJSON.getJSONObject("data");

        if(data.get("@kind").equals("interaction")) {
            String message = "someone waved at you!";
        }

        return new Notification(0, 2, "imagem");
    }
*/
    // TODO should test
    public static ArrayList<UserThumb> userThumbsInsideFromJSON(JSONObject jsonData) throws JSONException {
        JSONArray jsonListOfUsers = jsonData.getJSONArray("list");
        int numOfUsers = jsonListOfUsers.length();

        ArrayList<UserThumb> usersInside = new ArrayList<>();
        for(int i = 0; i < numOfUsers; i++) {
            UserThumb userThumb = new UserThumb( jsonListOfUsers.getJSONObject(i) );
            usersInside.add(userThumb);
        }

        return usersInside;
    }

    public static ArrayList<Notification> notificationFromJSON(JSONObject jsonData) throws JSONException {
        JSONArray notificationsArrayJson = jsonData.getJSONArray("list");
        int numOfNotifications = notificationsArrayJson.length();

        ArrayList<Notification> notifications = new ArrayList<>();
        for(int i = 0; i < numOfNotifications; i++) {
            Notification notification = new Notification( notificationsArrayJson.getJSONObject(i) );
            notifications.add(notification);
        }

        return notifications;
    }
/*
    public static ArrayList<Notification> interactionsFromJSON(JSONObject notificationsInsideJSON) throws JSONException {
        JSONObject usersDatajson = notificationsInsideJSON.getJSONObject("data");
        JSONArray jsonListOfUsers = notificationsInsideJSON.getJSONArray("list");

        int numOfUsers = usersDatajson.getInt("quantity");
        ArrayList<Notification> notificationsInside = new ArrayList<>();

        for(int i = 0; i < numOfUsers; i++) {
            notificationsInside.add(userFromJSON(jsonListOfUsers.getJSONObject(i)));
            Log.d("USER " + i + ": ", notificationsInside.toString());
        }

        return notificationsInside;
    }*/
}
