package outspin.mvp.radar.api;

import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import outspin.mvp.radar.models.User;

public class SignUp implements APICallBack {
   private User user;
   private String password;

   public SignUp(User user, String password) {
        this.user = user;
        this.password = password;
   }

    @Override
    public void complete(JSONObject json) {
    }

    @Override
    public API.APIConnectionBundle getAPIConnectionBundle() {
       JSONObject json = null;
       try {
            json = JSONBuilder.standardJSON();
            JSONObject userJson = JSONBuilder.JSONFromUser(user);
            userJson.put("password", password);
            json.put("data", userJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new API.APIConnectionBundle("POST", "users", new HashMap<String, String>(), json);
    }
}
