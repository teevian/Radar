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
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import outspin.mvp.radar.data.Macros;
import outspin.mvp.radar.models.UserThumb;

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
    public static Uri buildUri(@NonNull String[] paths, Map<String, String> queries)
            throws UnsupportedEncodingException {
        // builder must have scheme https
        Uri.Builder builder = Uri.parse("https://92.222.10.201:62126").buildUpon();

        for(String path : paths) builder.appendPath(path);
        for(Map.Entry<String, String> entry : queries.entrySet())
            builder.appendQueryParameter(
                    entry.getKey(),
                    URLEncoder.encode(entry.getValue(), java.nio.charset.StandardCharsets.UTF_8.name())
            );

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
    public static HttpsURLConnection openAPIConnection(String httpsMethod, @NonNull URL endpoint, long size) throws IOException {
        //URL endpoint = new URL(url.toString());
        HttpsURLConnection connection = (HttpsURLConnection) endpoint.openConnection();

        connection.setRequestProperty("Accept-Charset", java.nio.charset.StandardCharsets.UTF_8.name());
        connection.setRequestProperty("User-Agent", Macros.CONST_OUTSPIN_USER_AGENT);
        connection.setRequestProperty("Content-Type", "application/outspin.api+json");

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
    public static JSONObject getResponseFromRequest(@NonNull HttpsURLConnection connection, JSONObject jsonRequest)
            throws IOException, JSONException {

        if(jsonRequest != null) {   // sends the request to the server
            OutputStream outStream = connection.getOutputStream();
            OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, StandardCharsets.UTF_8);

            outStreamWriter.write(jsonRequest.toString());

            outStreamWriter.close();
            outStream.close();
        }

        // receives the response from the server
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();

        String responseLineFromAPI;
        while ((responseLineFromAPI = bufferedReader.readLine()) != null)
            stringBuilder.append(responseLineFromAPI).append("\n");

        bufferedReader.close();
        String responseString = stringBuilder.toString();

        return JSONBuilder.JSONfromString(responseString);
    }

    /**
     * Parses the Server JSON Response in the API context.
     *
     * @param statusCode status code response
     * @param jsonResponse json object received from the server
     * @return API context object, APIErrorResponse if @statusCode is not 200
     * @throws JSONException json was not parsed right
     */
    public APIResponse getAPIResponseObject(int statusCode, JSONObject jsonResponse) throws JSONException {
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
     *
     *
     * @param id
     * @return
     */
    public ArrayList<UserThumb> getUsersThumbById(@NonNull long... id) {
        Map<String, String> queries = new HashMap<>();
        queries.put("id", String.valueOf(id[0])); //only one user
        Uri uri = null;
        try {
            uri = buildUri(new String[] {"users"}, queries);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        JSONObject responseJson = null;
        HttpsURLConnection urlAPIConnection = null;
        try {
            URL url = new URL(uri.toString());
            urlAPIConnection = openAPIConnection("GET", url, -1);
            responseJson = getResponseFromRequest(urlAPIConnection, null);
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

    /**
     *
     */
    public static class QueryAPI extends AsyncTask<Void, String, JSONObject> {
        private final APIConnectionBundle apiBundle;
        private final APICallBack apiCallBack;

        public QueryAPI(@NonNull APICallBack apiCallBack) {
            this.apiBundle = apiCallBack.getAPIConnectionBundle();
            this.apiCallBack = apiCallBack;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(Void... object) {
            HttpsURLConnection urlConnection = null;
            JSONObject jsonFromServer = null;

            try {
                Uri uri = buildUri(apiBundle.paths, apiBundle.queries);
                URL url = new URL(uri.toString());
                urlConnection = openAPIConnection(apiBundle.httpMethod, url, -1);
                jsonFromServer = getResponseFromRequest(urlConnection, apiBundle.json);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                assert urlConnection != null;
                urlConnection.disconnect();
            }
            return jsonFromServer;
        }

        @Override
        protected void onPostExecute(JSONObject jsonFromServer) {
            apiCallBack.complete(jsonFromServer);
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
}
