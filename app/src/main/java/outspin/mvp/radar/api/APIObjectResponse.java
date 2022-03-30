package outspin.mvp.radar.api;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class APIObjectResponse extends APIResponse {
    JSONObject data;

    public APIObjectResponse(@NonNull JSONObject responseJSON) throws JSONException {
        super(responseJSON);

        this.type = "object";
    }

    @Override
    void setData() throws JSONException {
        this.data = this.dataJSON.getJSONObject(0);
    }

}
