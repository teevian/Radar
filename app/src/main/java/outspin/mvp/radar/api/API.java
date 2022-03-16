package outspin.mvp.radar.api;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import outspin.mvp.radar.data.Macros;
import outspin.mvp.radar.models.UserThumb;

public class API {
    private static final Set<String> outputHttpMethods = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList("POST", "PUT", "PATCH", "DELETE"))
    );

    public static class APIConnectionBundle {
        protected String httpMethod;
        protected JSONObject json;
        protected Map<String, String> queries;
        protected String path;

        public APIConnectionBundle(@NonNull String method, @NonNull String path, Map<String, String> queries, JSONObject json) {
            this.httpMethod = method;
            this.json = json;
            this.path = path;
            this.queries = queries;
        }


        public JSONObject getJson() {
            return json;
        }
    }

    public static Uri buildUri(String path, Map<String, String> queries) {
        Uri.Builder builder = Uri.parse("http://92.222.10.201:62126").buildUpon();
        builder.appendPath(path).appendPath("register");

        for(Map.Entry<String, String> entry : queries.entrySet())
            builder.appendQueryParameter(entry.getKey(), entry.getValue());

        return builder.build();
    }

    public static HttpURLConnection openAPIConnection(String httpMethod, Uri uri, JSONObject json) throws IOException {
        URL url = new URL(uri.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("Accept-Charset", java.nio.charset.StandardCharsets.UTF_8.name());
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod(httpMethod);
        connection.setDoInput(true);

        connection.setConnectTimeout(30000);    // timeout for connection
        connection.setReadTimeout(15000);       // timeout for not receiving bytes

        connection.setInstanceFollowRedirects("GET".equals(httpMethod));

        if(!outputHttpMethods.contains(httpMethod) || json == null) {
            connection.setDoOutput(false);
        }
        else {
            connection.setDoOutput(true);
            OutputStream outStream = connection.getOutputStream();
            OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "UTF-8");
            outStreamWriter.write(json.toString());
            outStreamWriter.close();
            outStream.close();
        }
        return connection;
    }


    public static JSONObject getAPIResponseFromConnection(@NonNull HttpURLConnection urlAPIConnection)
            throws IOException, JSONException {

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(urlAPIConnection.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();

        String responseLineFromAPI;
        while ((responseLineFromAPI = bufferedReader.readLine()) != null)
            stringBuilder.append(responseLineFromAPI).append("\n");

        bufferedReader.close();
        String responseString = stringBuilder.toString();
        Log.d("JSON:::::::::::", responseString);
        JSONObject responseJson = JSONBuilder.JSONfromString(responseString);

        int statusCode = urlAPIConnection.getResponseCode();
        String statusMessage = urlAPIConnection.getResponseMessage();
        switch (statusCode) {
            case Macros.SERVER_STATUS_OK:
                Log.i("Everything is OK!", "" + statusCode);
                break;
            case Macros.SERVER_STATUS_NOT_FOUND:
            case Macros.SERVER_STATUS_TIMED_OUT:
            default:
                Log.e("ERROR", statusMessage + " : " + statusCode);
                break;
        }

        return responseJson;
    }

    public static class QueryAPI extends AsyncTask<Void, String, JSONObject> {
        private final APIConnectionBundle bundle;
        private final APICallBack apiCallBack;

        public QueryAPI(APICallBack apiCallBack) {
            this.bundle = apiCallBack.getAPIConnectionBundle();
            this.apiCallBack = apiCallBack;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(Void... object) {
            HttpURLConnection urlConnection = null;
            JSONObject json = null;

            try {
                urlConnection = openAPIConnection(bundle.httpMethod, buildUri(bundle.path, bundle.queries), bundle.json);
                json = getAPIResponseFromConnection(urlConnection);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonResult) {
            apiCallBack.complete(jsonResult);
        }
    }
}
