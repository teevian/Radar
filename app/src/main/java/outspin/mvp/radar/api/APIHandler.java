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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import outspin.mvp.radar.data.Macros;
import outspin.mvp.radar.models.UserThumb;

public class APIHandler {
    private final String serverIPv4 = Macros.CONST_INTERNET_SERVER_IPV4;
    private final String host = serverIPv4;
    private final int port = 62126;
    private static final Set<String> outputHttpMethods = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList("POST", "PUT", "PATCH", "DELETE"))
    );

    public Uri buildUri(String path, Map<String, String> queries) {
        Uri.Builder builder = Uri.parse(host + ":" + port).buildUpon();
        builder.appendPath(path);

        for(Map.Entry<String, String> entry : queries.entrySet())
            builder.appendQueryParameter(entry.getKey(), entry.getValue());

        return builder.build();
    }

    public HttpURLConnection openAPIConnection(String httpMethod, String urlString) throws IOException {
        Log.d("oooooooooooo", urlString);

        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("Accept-Charset", java.nio.charset.StandardCharsets.UTF_8.name());
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod(httpMethod);
        connection.setDoInput(true);

        connection.setConnectTimeout(30000);    // timeout for connection
        connection.setReadTimeout(15000);       // timeout for not receiving bytes

        connection.setInstanceFollowRedirects("GET".equals(httpMethod));
        connection.setDoOutput(outputHttpMethods.contains(httpMethod));

        return connection;
    }


    /*
        // TODO(1) idk faz isto
        public static boolean userExists(int id) {
            String uri = "?id=" + String.valueOf(id);
            APIConnectionBundle bundle = new APIConnectionBundle("GET", uri, json);

            QueryAPI queryAPI = new QueryAPI(bundle);
            queryAPI.execute();

            return false;
        }

        public static boolean userExists(String phoneNumber) {

            APIConnectionBundle bundle = new APIConnectionBundle("GET", "");

            return false;
        }

        // TODO(2) joao birras faz isto
        public static UserThumbnail createUser() {
            //connect.onPostExecute("GET", UserThumbnail);
            APIConnectionBundle bundle = new APIConnectionBundle("GET", "users", "dasd");

            //ConnectAPI(bundle);

            return null;
        }

    */

    public JSONObject getAPIResponseFromConnection(@NonNull HttpURLConnection urlAPIConnection)
            throws IOException, JSONException {

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(urlAPIConnection.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();

        String responseLineFromAPI;
        while ((responseLineFromAPI = bufferedReader.readLine()) != null)
            stringBuilder.append(responseLineFromAPI).append("\n");

        bufferedReader.close();
        String responseString = stringBuilder.toString();
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

    public ArrayList<UserThumb> getUsersThumbById(@NonNull long... id) {
        Map<String, String> queries = new HashMap<>();
        queries.put("id", String.valueOf(id[0])); //only one user
        Uri uri = buildUri("users", queries);

        JSONObject responseJson = null;
        HttpURLConnection urlAPIConnection = null;

        try {
            Log.d("oooooooooooo", "T");
            urlAPIConnection = openAPIConnection("GET", uri.toString());
            responseJson = getAPIResponseFromConnection(urlAPIConnection);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlAPIConnection != null) urlAPIConnection.disconnect();
        }

        // construct array
        ArrayList<UserThumb> users = new ArrayList<>();
        users.add(new UserThumb(responseJson));

        return users;
    }

    public static class QueryAPI extends AsyncTask<Void, String, JSONObject> {
        private final APIConnectionBundle bundle;

        QueryAPI(APIConnectionBundle bundle) {
            this.bundle = bundle;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(Void... object) {
            // TODO(3) build URL
            HttpURLConnection urlConnection = null;
            String jsonString = null;

            try {
                URL url = bundle.getURL();
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod(bundle.httpMethod);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoOutput(bundle.setDoOutput);
                urlConnection.setDoInput(bundle.setDoInput);

                int statusCode = urlConnection.getResponseCode();

                if(statusCode == Macros.SERVER_STATUS_OK) {
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
                    StringBuilder stringBuilder = new StringBuilder();

                    String responseLineFromAPI;
                    while ((responseLineFromAPI = bufferedReader.readLine()) != null) {
                        stringBuilder.append(responseLineFromAPI).append("\n");
                    }
                    bufferedReader.close();

                    jsonString = stringBuilder.toString();
                    Log.d("SERVER OUTPUT::::", jsonString);

                    JSONObject jsonUser = JSONBuilder.JSONfromString(jsonString);
                    UserThumb userThumb = JSONBuilder.userFromJSON(jsonUser);

                    Log.d("USER::::::::", userThumb.toString());
                } else {
                    Log.d("STATUS CODE", "NOT OK");
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            JSONObject json = null;
            try {
                json = JSONBuilder.JSONfromString(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonResult) {

        }

    }

    public ArrayList<UserThumb> getUsersFromClub(long clubID) {
        String test = "{\"meta\":{\"apiVersion\":\"0.1\"},\"data\":{\"quantity\":4,\"list\":[{\"@kind\":\"user\",\"id\":1,\"thumbnail\":\"https://turingnotes.com/wp-content/uploads/2022/02/photo16.jpeg\",\"name\":\"\"},{\"@kind\":\"user\",\"id\":2,\"thumbnail\":\"https://turingnotes.com/wp-content/uploads/2022/02/photo16.jpeg\",\"name\":\"\"},{\"@kind\":\"user\",\"id\":3,\"thumbnail\":\"https://turingnotes.com/wp-content/uploads/2022/02/photo16.jpeg\",\"name\":\"\"},{\"@kind\":\"user\",\"id\":4,\"thumbnail\":\"https://turingnotes.com/wp-content/uploads/2022/02/photo16.jpeg\",\"name\":\"\"}]}}\n";

        APIConnectionBundle bundle = new APIConnectionBundle("GET", "?id=1&club=2");
        QueryAPI query = new QueryAPI(bundle);
        query.execute();



        ArrayList<UserThumb> users = null;

        try {
            users = JSONBuilder.userThumbsInsideFromJSON( JSONBuilder.JSONfromString(test) );

            Log.d("USERS:::::", users.toString());
        } catch(Exception ignored) {}

        return users;
    }

    public static class APIConnectionBundle {
        protected final String hostIP = "http://92.222.10.201";
        protected final String port = String.valueOf(Macros.CONST_INTERNET_TCP_PORT);
        protected String httpMethod;
        protected JSONObject json;
        protected final String contentType = "application/json";
        protected boolean setDoOutput = false;
        protected boolean setDoInput = true;
        protected String uri;
        protected String URL;
        protected String fullURL;
        protected String test = "http://92.222.10.201:62126/users?id=2";

        public APIConnectionBundle(@NonNull String method, @NonNull String uri, JSONObject json) {
            this.httpMethod = method;
            this.uri = uri;
            this.json = json;
            this.URL = this.hostIP + ":" + this.port + "/";
            this.fullURL = this.URL + "";
        }

        public APIConnectionBundle(@NonNull String method, @NonNull String uri) {
            this.httpMethod = method;
            this.uri = uri;
            this.URL = this.hostIP + ":" + this.port + "/";
        }

        public URL getURL() throws MalformedURLException {
            return new URL(this.URL + this.uri);
        }
    }


}
