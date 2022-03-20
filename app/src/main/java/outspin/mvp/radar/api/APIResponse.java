package outspin.mvp.radar.api;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class APIResponse {
    protected JSONArray dataJSONArray;

    protected String apiVersion;

    public APIResponse(@NonNull JSONObject jsonResponse) throws JSONException {
        this.dataJSONArray = jsonResponse.getJSONArray("data");

        JSONObject meta = jsonResponse.getJSONObject("meta");
        this.apiVersion = meta.getString("apiVersion");

        setData();
    }

    abstract void setData() throws JSONException;

    /*  getters & setters   */
    public JSONArray getDataJSONArray() { return dataJSONArray; }
    public void setDataJSONArray(JSONArray dataJSONArray) { this.dataJSONArray = dataJSONArray; }

    public String getApiVersion() { return apiVersion; }
    public void setApiVersion(String apiVersion) { this.apiVersion = apiVersion; }
}
