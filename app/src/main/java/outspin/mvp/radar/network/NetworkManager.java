package outspin.mvp.radar.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import outspin.mvp.radar.data.Macros;
import outspin.mvp.radar.models.UserThumbnail;
import outspin.mvp.radar.utils.JSONBuilder;

public class NetworkManager {



        public static class JSONTask extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... strings) {
                HttpURLConnection urlConnection = null;
                String jsonString = null;
                try {
                    URL url = new URL("http://92.222.10.201:62126/users?id=4");

                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setDoOutput(false);

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
        }

    // Returns connection type. 0: none; 1: mobile data; 2: wifi; 3: vpn;
    @IntRange(from = 0, to = 3)
    public static int getConnectionType(@NonNull Context context) {
        int result = 0;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                    result = 2;
                else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                    result = 1;
                else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN))
                    result = 3;
            }
        }
        return result;
    }

    public static class CheckInternetConnection extends AsyncTask<Void, Void, Boolean> {
        Context context;

        public CheckInternetConnection(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //Returns connection type. 0: none; 1: mobile data; 2: wifi; 3: vpn; 4: server not reachable
        @Override
        protected Boolean doInBackground(Void... voids) {
            int type = getConnectionType(this.context);
            return type != 0 && isServerReachable(Macros.CONST_INTERNET_SOCKET_TIMEOUT);
        }

        @Override
        protected void onPostExecute(Boolean isConnected) {
            super.onPostExecute(isConnected);

     }

        public static boolean isServerReachable(int timeoutMS) {
            boolean connected = false;
            Socket socket;

            try {
                socket = new Socket();
                SocketAddress socketAddress =
                        new InetSocketAddress(Macros.CONST_INTERNET_SERVER_IPV4, Macros.CONST_INTERNET_TCP_PORT);
                socket.connect(socketAddress, timeoutMS);

                if (socket.isConnected()) {
                    connected = true;
                    socket.close();
                }
            } catch (SocketTimeoutException timeoutException) {
                Log.d("EXCEPTION:", "TIME OUT");
                socket = null;
                connected = false;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                socket = null;
            }
            return connected;
        }
    }
}
