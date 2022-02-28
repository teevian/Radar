package outspin.mvp.radar.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

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

import outspin.mvp.radar.models.User;

public class NetworkManager {

        public static class JSONTask extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... strings) {

                HttpURLConnection connection = null;
                BufferedReader reader = null;

                try {
                    // TODO(10.2) IMPORTANT: SHOULD BE HTTPS AND THIS SHOULD BE FALSE (CHECK 10.1)
                    URL url = new URL("http://92.222.10.201:62126/dummy/users");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    InputStream stream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuilder buffer = new StringBuilder();

                    String line = "";
                    Log.d("OH OH", "NOT A TEST");

                    while(null != (line = reader.readLine())) {
                        buffer.append(line).append("\n");
                        Log.d("JSON from server: ", line);
                    }

                    return buffer.toString();
                } catch (IOException e) {
                    Log.d("ERROR", "COULDNT CONNECT");
                    e.printStackTrace();
                } finally {
                    if(connection != null) {
                        connection.disconnect();
                    }
                    try {
                        if(reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(String jsonResult) {
                super.onPostExecute(jsonResult);

                try {
                    JSONArray json = new JSONArray(jsonResult);
                    int lenght = json.length();

                    //for(int i = 0; i < lenght; i++) {
                    //    JSONObject jsonArray = json.getJSONObject(i);
                    Log.d("JSON: ", json.getJSONObject(0).toString());

                    } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }


/*
    // Returns connection type. 0: none; 1: mobile data; 2: wifi; 3: vpn;
    @IntRange(from = 0, to = 3)
    public static int getConnectionType(@NonNull Context context) {
        int result = 0;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
        } else {
            if (cm != null) {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    // connected to the internet
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                        result = 2;
                    else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                        result = 1;
                    else if (activeNetwork.getType() == ConnectivityManager.TYPE_VPN)
                        result = 3;
                }
            }
        }
        return result;
    }

    //public static class BindMoment extends AsyncTask<Void, Void, Boolean> {
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

    */
}
