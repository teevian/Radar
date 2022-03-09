package outspin.mvp.radar.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import outspin.mvp.radar.models.UserThumbnail;

public class JSONBuilder {
    
    public static JSONObject JSONfromString(String string) throws JSONException {
        return new JSONObject(string);
    }

    public static UserThumbnail UserFromJSON(JSONObject json) throws JSONException {
        JSONObject data = json.getJSONObject("data");

        String userName = (String) data.get("name");
        String phoneNumber = (String) data.get("phoneNumber");

        return new UserThumbnail(userName, phoneNumber, "sed");
    }
}
