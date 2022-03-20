package outspin.mvp.radar.api;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Handles error within the app.
 */
public class APIErrorResponse extends APIResponse {
    private int statusCode;
    private String title;
    private String detail;
    private String message;
    private String stackTrace;

    public APIErrorResponse(@NonNull JSONObject errorJSON) throws JSONException {
        super(errorJSON);
    }

    @Override
    void setData() throws JSONException {
        JSONObject errorData = this.dataJSONArray.getJSONObject(0);

        this.statusCode = errorData.getInt("code");
        this.title      = errorData.getString("title");
        this.message    = errorData.getString("message");
        this.detail     = errorData.getString("detail");
        this.stackTrace = errorData.getString("stackTrace");
    }

    public boolean reportError() {
        return false;
    }

    /*  getters & setters   */
    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getStackTrace() { return stackTrace; }
    public void setStackTrace(String stackTrace) { this.stackTrace = stackTrace; }
}

/*
Content-Type: application/oustpin.api+json
{
    "meta" :
        {
            "apiVersion" : "0.1"
        }
    "data":
        {
            "kind" : "error",
            "code" : 404,
            "status" : "NOT FOUND",
            "title" : "",
            "detail" : "",
            "message" : ""
        }
}
 */