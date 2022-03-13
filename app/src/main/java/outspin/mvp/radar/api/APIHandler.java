package outspin.mvp.radar.api;

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

import outspin.mvp.radar.data.Macros;
import outspin.mvp.radar.models.UserThumbnail;

public class APIHandler {

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
    public boolean openConnection() {
        return false;
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
                    UserThumbnail userThumbnail = JSONBuilder.userFromJSON(jsonUser);

                    Log.d("USER::::::::", userThumbnail.toString());
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

    public ArrayList<UserThumbnail> getUsersFromClub(long clubID) {
        String test = "{\"meta\":{\"apiVersion\":\"0.1\"},\"data\":{\"quantity\":4,\"list\":[{\"@kind\":\"user\",\"id\":1,\"thumbnail\":\"https://turingnotes.com/wp-content/uploads/2022/02/photo16.jpeg\",\"name\":\"\"},{\"@kind\":\"user\",\"id\":2,\"thumbnail\":\"https://turingnotes.com/wp-content/uploads/2022/02/photo16.jpeg\",\"name\":\"\"},{\"@kind\":\"user\",\"id\":3,\"thumbnail\":\"https://turingnotes.com/wp-content/uploads/2022/02/photo16.jpeg\",\"name\":\"\"},{\"@kind\":\"user\",\"id\":4,\"thumbnail\":\"https://turingnotes.com/wp-content/uploads/2022/02/photo16.jpeg\",\"name\":\"\"}]}}\n";

        APIConnectionBundle bundle = new APIConnectionBundle("GET", "?id=1&club=2");
        QueryAPI query = new QueryAPI(bundle);
        query.execute();



        ArrayList<UserThumbnail> users = null;

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
