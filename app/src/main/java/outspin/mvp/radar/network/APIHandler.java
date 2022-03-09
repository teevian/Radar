package outspin.mvp.radar.network;

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

import outspin.mvp.radar.data.Macros;
import outspin.mvp.radar.models.UserThumbnail;
import outspin.mvp.radar.utils.JSONBuilder;

public class APIHandler {

    // TODO(1) idk faz isto
    public static boolean userExists() {

        return false;
    }

    // TODO(2) joao birras faz isto
    public static UserThumbnail createUser() {
        //connect.onPostExecute("GET", UserThumbnail);
        APIConnectionBundle bundle = new APIConnectionBundle("GET", "users", "dasd");

        //ConnectAPI(bundle);

        return null;
    }


    public static class ConnectAPI extends AsyncTask<Void, String, String> {
        private String serverPort = String.valueOf(Macros.CONST_INTERNET_TCP_PORT);
        private String hostIP = Macros.CONST_INTERNET_SERVER_IPV4;
        private APIConnectionBundle bundle;

        ConnectAPI(APIConnectionBundle bundle) {
            this.bundle = bundle;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... object) {
            // TODO(3) build URL
            // connection
            // parse object
            // return object
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
                        stringBuilder.append(responseLineFromAPI + "\n");
                    }
                    bufferedReader.close();

                    jsonString = stringBuilder.toString();
                    Log.d("SERVER OUTPUT::::", jsonString);

                    JSONObject jsonUser = JSONBuilder.JSONfromString(jsonString);
                    UserThumbnail userThumbnail = JSONBuilder.UserFromJSON(jsonUser);

                    Log.d("USER::::::::", userThumbnail.toString());
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonString;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String jsonResult) {
            super.onPostExecute(jsonResult);

            //Log.d("SERVER OUT:::::", jsonResult);
        }

        public HttpURLConnection getURLConnection() throws MalformedURLException {
            URL url = new URL("http://92.222.10.201:62126/users?id=4");
            return null;
        }
    }

    protected static class APIConnectionBundle {
        protected final String hostIP = "http://92.222.10.201";
        protected final String port = String.valueOf(Macros.CONST_INTERNET_TCP_PORT);
        protected String httpMethod;
        protected String json;
        protected boolean setDoOutput = false;
        protected boolean setDoInput = true;
        protected String uri;
        protected String URL;

        APIConnectionBundle(@NonNull String method, @NonNull String uri, String json) {
            this.httpMethod = method;
            this.uri = uri;
            this.json = json;
            this.URL = this.hostIP + ":" + this.port + "/";
        }

        public URL getURL() throws MalformedURLException {
            return new URL(this.URL + this.uri);
        }
    }
}
