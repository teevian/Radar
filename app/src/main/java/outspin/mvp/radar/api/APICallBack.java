package outspin.mvp.radar.api;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.util.Map;

public interface APICallBack {

    public void complete(JSONObject json);

    public API.APIConnectionBundle getAPIConnectionBundle();
}
