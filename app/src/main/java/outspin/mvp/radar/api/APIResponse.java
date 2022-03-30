package outspin.mvp.radar.api;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class APIResponse {
    protected String type;
    protected JSONArray dataJSON;

    protected String apiVersion;

    public APIResponse(@NonNull JSONObject jsonResponse) {
        try {
            JSONObject meta = jsonResponse.getJSONObject("meta");
            this.apiVersion = meta.getString("apiVersion");

            this.dataJSON = jsonResponse.getJSONArray("data");

            setData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public APIResponse() {
        
    }

    abstract void setData() throws JSONException;

    /*  getters & setters   */
    public JSONArray getDataJSON() { return dataJSON; }
    public void setDataJSON(JSONArray dataJSON) { this.dataJSON = dataJSON; }

    public String getApiVersion() { return apiVersion; }
    public void setApiVersion(String apiVersion) { this.apiVersion = apiVersion; }
}
