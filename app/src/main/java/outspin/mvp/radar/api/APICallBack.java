package outspin.mvp.radar.api;

import org.json.JSONObject;

public interface APICallBack {

    void complete(JSONObject json);
    APIHandler.APIConnectionBundle getAPIConnectionBundle();
}
