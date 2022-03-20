package outspin.mvp.radar.api;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class APIObjectResponse extends APIResponse {
    JSONObject data;

    public APIObjectResponse(@NonNull JSONObject responseJSON) throws JSONException {
        super(responseJSON);
    }

    @Override
    void setData() throws JSONException {
        this.data = this.dataJSONArray.getJSONObject(0);
    }

}
