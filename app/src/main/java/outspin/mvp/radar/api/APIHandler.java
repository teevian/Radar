package outspin.mvp.radar.api;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import outspin.mvp.radar.data.Macros;
import outspin.mvp.radar.models.Thumbnail;

public class APIHandler {
    private static final int CONNECTION_TIMEOUT_IN_MILISECONDS  = 30000;
    private static final int READ_TIMEOUT_IN_MILISECONDS        = 15000;

    private static final Set<String> outputHttpMethods = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList("POST", "PUT", "PATCH", "DELETE")) );

    /**
     * Builds the URI in order to connect with the API.
     *
     * @param paths array with paths
     * @param queries queries to send
     * @return uri built, null if not processed
     * @throws UnsupportedEncodingException if encoding UTF-8 is not supported
     */
    public static Uri buildUri(Map<String, String> queries, String... paths)
            throws UnsupportedEncodingException {
        Uri.Builder builder = Uri.parse("http://92.222.10.201:62126").buildUpon();

        for(String path : paths) builder.appendPath(path);

        if(!queries.isEmpty())
        for(Map.Entry<String, String> entry : queries.entrySet())
            builder.appendQueryParameter(
                    entry.getKey(),
                    URLEncoder.encode(entry.getValue(), java.nio.charset.StandardCharsets.UTF_8.name())
            );

        return builder.build();
    }

    /**
     * Builds the URI in order to connect with the API.
     *
     * @param paths array with paths
     * @param queries queries to send
     * @return uri built, null if not processed
     * @throws UnsupportedEncodingException if encoding UTF-8 is not supported
     */
    public static Uri buildUri(Set<Pair<String, String>> queries, String... paths)
            throws UnsupportedEncodingException {
        Uri.Builder builder = Uri.parse("http://92.222.10.201:62126").buildUpon();

        for(String path : paths) builder.appendPath(path);

        if(!queries.isEmpty())
        for(Pair<String, String> query : queries) {

            builder.appendQueryParameter(
                    query.first,
                    URLEncoder.encode(query.second, java.nio.charset.StandardCharsets.UTF_8.name())
            );
        }
        return builder.build();
    }

    /**
     * Opens a connection with the Server API.
     *
     * @param httpsMethod method for the connection: POST, GET, PUT, DELETE, etc
     * @param endpoint connection's uri
     * @param size size of the data to be transfered (-1 if unknown)
     * @return a copy of the opened connection
     * @throws IOException
     */
    @NonNull
    public static HttpURLConnection openAPIConnection(String httpsMethod, @NonNull URL endpoint, long size)
            throws IOException {
        HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();

        //connection.setRequestProperty("Accept-Charset", java.nio.charset.StandardCharsets.UTF_8.name());
        //connection.setRequestProperty("User-Agent", Macros.CONST_OUTSPIN_USER_AGENT);
        //connection.setRequestProperty("Content-Type", "application/outspin.api+json");
        connection.setRequestProperty("Content-Type", "application/json");

        connection.setRequestMethod(httpsMethod);
        connection.setDoInput(true);

        // improves performance by setting a fixed size to the buffer stream in order to transfer data
        if(size <= 0) connection.setChunkedStreamingMode(0);    // if data size to send is unknown
        else connection.setFixedLengthStreamingMode(size);      // if data size to send is known

        connection.setConnectTimeout(CONNECTION_TIMEOUT_IN_MILISECONDS);    // timeout for connection
        connection.setReadTimeout(READ_TIMEOUT_IN_MILISECONDS);             // timeout for not receiving bytes

        connection.setInstanceFollowRedirects("GET".equals(httpsMethod));
        connection.setDoOutput(outputHttpMethods.contains(httpsMethod));

        return connection;
    }

    /**
     * Gets a JSON response from the API using a connection.
     *
     * @param connection url for the API connection
     * @param jsonRequest json to send to Server
     * @return response from Server
     * @throws IOException
     * @throws JSONException
     */
    @NonNull
    public static JSONObject getResponseFromRequest(@NonNull HttpURLConnection connection, @NonNull JSONObject jsonRequest)
            throws IOException, JSONException {

        Log.d("JJJJJJJJJJ->", jsonRequest.toString());
        Log.d("JJJJJJJJJJ->", connection.getRequestMethod() + " " + connection.getResponseCode());

        // sends the request to the server
        OutputStream outStream = connection.getOutputStream();
        OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, StandardCharsets.UTF_8);
        Log.d("JJJJJJJJJJ-> TEST", jsonRequest.toString());

        outStreamWriter.write(jsonRequest.toString());

        outStreamWriter.close();
        outStream.close();

        Log.d("YYYYYYYY//", jsonRequest.toString());

        // receives the response from the server
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();

        Log.d("YYYYYYYY", stringBuilder.toString());

        String responseLineFromAPI;
        while ((responseLineFromAPI = bufferedReader.readLine()) != null)
            stringBuilder.append(responseLineFromAPI).append("\n");

        bufferedReader.close();
        String responseString = stringBuilder.toString();

        Log.d("YYYYYYYY", responseString);

        return JSONParser.JSONfromString(responseString);
    }

    /**
     * Parses the Server JSON Response in the API context.
     *
     * @param statusCode status code response
     * @param jsonResponse json object received from the server
     * @return API context object, APIErrorResponse if @statusCode is not 200
     * @throws JSONException json was not parsed right
     */
    public APIResponse APIResponseFromJSON(int statusCode, JSONObject jsonResponse) throws JSONException {
        APIResponse response;
        switch (statusCode) {
            case HttpsURLConnection.HTTP_OK:
                response = new APIObjectResponse(jsonResponse);
                break;
            case HttpsURLConnection.HTTP_NOT_FOUND:         // user does not exist
            case HttpsURLConnection.HTTP_CLIENT_TIMEOUT:    // connection timed out
            default:
                response = new APIErrorResponse(jsonResponse);
                break;
        }
        return response;
    }

    /**
     * Get users from ids.
     *
     * @param ids String[] ids
     * @return arraylist of user thumbs
     */
    @NonNull
    public static ArrayList<Thumbnail> getUsersThumbById(@NonNull long... ids) {
        Set<Pair<String, String>> queries = new HashSet<>();
        for(long id : ids) queries.add(new Pair<>("id", String.valueOf(id)));

        JSONObject responseJson;
        HttpURLConnection urlAPIConnection = null;
        ArrayList<Thumbnail> users = new ArrayList<>();
        try {
            URL endpoint = new URL(buildUri(queries, "users").toString());

            urlAPIConnection = openAPIConnection("GET", endpoint, -1);
            responseJson = getResponseFromRequest(urlAPIConnection, null);

            JSONArray data = responseJson.getJSONArray("data");
            for(int i = 0; i < data.length(); i++)
                users.add(new Thumbnail(data.getJSONObject(i)));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlAPIConnection != null) urlAPIConnection.disconnect();
        }
        return users;
    }

    /**
     *
     */
    public static class QueryAPI extends AsyncTask<String, String, JSONObject> {
        private final APIConnectionBundle apiBundle;
        private final APIConnectionCallback apiConnectionCallback;

        private int responseCode;
        private String responseMessage;

        public QueryAPI(@NonNull APIConnectionCallback callback) {
            this.apiBundle = callback.getAPIConnectionBundle();
            this.apiConnectionCallback = callback;
        }

        @Override
        protected JSONObject doInBackground(String... object) {
            HttpURLConnection urlConnection = null;
            JSONObject jsonFromServer = null;

            try {
                //URL url = new URL(buildUri(apiBundle.queries, apiBundle.paths).toString());
                //URL url = new URL("http://92.222.10.201:62126/users/login");
                //urlConnection = openAPIConnection(apiBundle.httpMethod, url, -1);
                //Log.d("TTTTTTTT", urlConnection.getResponseMessage());
                URL url = new URL("http://92.222.10.201:62126/users/login");

                // TODO
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                String json = "{\"meta\":{\"apiVersion\":\"0.1\"},\"data\":{\"kind\":\"login\",\"items\":[{\"phone\":\"912088808\",\"password\":\"wdwdwdwddwd\"}]}}";
                DataOutputStream os = new DataOutputStream(connection.getOutputStream());
                os.writeBytes(json.toString());

                os.flush();
                os.close();
                System.out.println("JSON: " + json);

                Log.d("TTTTTTTT--->>", connection.getResponseMessage());
                //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                /*
                try(OutputStream os = connection.getOutputStream()) {
                    byte[] input = json.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
*/
                String responseMessage = connection.getResponseMessage();
                int responseCode = connection.getResponseCode();

                connection.disconnect();

                //responseMessage = connection.getResponseMessage();
                //responseCode = connection.getResponseCode();

                //jsonFromServer = getResponseFromRequest(urlConnection, apiBundle.json);
                Log.d("TTTTTTTT--->", jsonFromServer.toString() + " THIS " + responseCode);

            } catch(IOException e /* | JSONException e*/) {
                APIErrorResponse apiError = new APIErrorResponse(e);
                e.printStackTrace();
            } finally {
                assert urlConnection != null;
                urlConnection.disconnect();
            }

            return jsonFromServer;
        }

        @Override
        protected void onPostExecute(JSONObject apiResponse) {
            if(responseCode < 400) apiConnectionCallback.onSuccess(apiResponse);
            else apiConnectionCallback.onFailure(new APIErrorResponse(apiResponse));
        }
    }

    /**
     * Inner helper class that holds values to sent the AsyncTask.
     */
    public static class APIConnectionBundle {
        protected String httpMethod;
        protected JSONObject json;
        protected Map<String, String> queries;
        protected String[] paths;

        public APIConnectionBundle(@NonNull String method,
                                   @NonNull String[] paths,
                                   Map<String, String> queries,
                                   JSONObject json) {
            this.httpMethod = method;
            this.json = json;
            this.paths = paths;
            this.queries = queries;
        }

        public JSONObject getJson() {
            return json;
        }
    }

    public interface APIConnectionCallback {
        void onSuccess(JSONObject jsonResponse);
        void onFailure(APIErrorResponse error);
        APIConnectionBundle getAPIConnectionBundle();
    }
}


/*
{
    "meta" : { "apiVersion" : "0.1"},
    "data" : {
        "kind" : "login",
        "items" : [
            { "phone" : "912088808", "password" : "ssS2iujsji" }
        ]
    }
}
 */