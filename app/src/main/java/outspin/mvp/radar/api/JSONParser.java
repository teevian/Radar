package outspin.mvp.radar.api;

import android.util.Log;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import outspin.mvp.radar.models.Interaction;
import outspin.mvp.radar.models.User;
import outspin.mvp.radar.models.Thumbnail;

public class JSONParser {
    
    @NonNull
    @Contract("_ -> new")
    public static JSONObject JSONfromString(String jsonString) throws JSONException {
        return new JSONObject(jsonString);
    }

    @NonNull
    public static  JSONObject getAPIJSONTemplate() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("apiVersion", "0.1");
        return json;
    }

    @NonNull
    public static JSONObject JSONFromUser(@NonNull User user) throws JSONException {
        JSONObject userJson = new JSONObject();
        String id = String.valueOf(user.getId());
        userJson.put("id", id);
        userJson.put("name", user.getName());
        userJson.put("phoneNumber", user.getPhoneNumber());
        userJson.put("countryCode", user.getCountryCode());
        userJson.put("photoURl", user.getPhotoURL());

        return userJson;
    }

    /**
     * Gets a list of users from a json response.
     *
     * @param jsonResponse json response from the server
     * @return list of users
     * @throws JSONException json key may not exist
     */
    @NonNull
    public static List<Thumbnail> usersFromJSON(@NonNull JSONObject jsonResponse) throws JSONException {
        JSONArray listOfUsersJSON = jsonResponse.getJSONObject("data").getJSONArray("users");

        List<Thumbnail> users = new ArrayList<>();
        int numOfUsers = listOfUsersJSON.length();
        for(int i = 0; i < numOfUsers; i++)
            users.add( new Thumbnail(listOfUsersJSON.getJSONObject(i)) );

        return users;
    }

    /**
     * gets a list of interactions from a json response.
     *
     * @param jsonResponse json response from the server
     * @return list of interactions
     * @throws JSONException json key may not exist
     */
    @NonNull
    public static List<Interaction> interactionsFromJSON(@NonNull JSONObject jsonResponse) throws JSONException {
        JSONArray listOfInteractionsJson = jsonResponse.getJSONObject("data").getJSONArray("interactions");

        Log.d("RRRRRRRRRRR", listOfInteractionsJson.toString());
        ArrayList<Interaction> interactions = new ArrayList<>();
        int numOfInteractions = listOfInteractionsJson.length();
        for(int i = 0; i < numOfInteractions; i++) {
            JSONObject interactionJSON = listOfInteractionsJson.getJSONObject(i);
            Log.d("RRRRRRRRRRRR", interactionJSON.toString());
            Interaction interaction = new Interaction(interactionJSON);
            Log.d("RRRRRRRRRRRRrrrrrrrrr", interaction.toString());
            interactions.add(interaction);
            Log.d("RRRRRRRR--------->", interactions.get(i).toString());
        }

        return interactions;
    }
}
