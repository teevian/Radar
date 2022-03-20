package outspin.mvp.radar.network;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

import outspin.mvp.radar.data.Macros;
import outspin.mvp.radar.models.UserThumb;
import outspin.mvp.radar.api.JSONBuilder;

public class NetworkManager {

    /**
     * Returns Internet connection type from System Connectivity Service.
     *
     * @param context context to get system service
     * @return connection type: 0: none; 1: mobile data; 2: wifi; 3: vpn
     */
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
