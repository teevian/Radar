package outspin.mvp.radar.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import outspin.mvp.radar.models.Notification;
import outspin.mvp.radar.models.UserThumb;

public class JSONBuilder {
    
    public static JSONObject JSONfromString(String jsonString) throws JSONException {
        return new JSONObject(jsonString);
    }

    public static UserThumb userFromJSON(JSONObject json) throws JSONException {
        JSONObject data = json.getJSONObject("data");

        String userName = (String) data.get("name");
        String phoneNumber = (String) data.get("phoneNumber");

        return new UserThumb(userName, phoneNumber, "sed");
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

    public static JSONObject getClubInfo(long id) {

        return null;
    }
}
