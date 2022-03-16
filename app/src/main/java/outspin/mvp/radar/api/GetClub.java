package outspin.mvp.radar.api;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GetClub implements APICallBack{
    Map<String, String> queries;
    String httpMethod;

    public GetClub() {
        this.httpMethod = "GET";
        this.queries = new HashMap<String, String>();
        this.queries.put("id", "7");
    }

    @Override
    public void complete(JSONObject json) {
        Log.d("API:::::::::::::", "Success");
    }

    @Override
    public API.APIConnectionBundle getAPIConnectionBundle() {
        return new API.APIConnectionBundle(this.httpMethod, "club", this.queries, null);
    }
}
