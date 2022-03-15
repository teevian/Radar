package outspin.mvp.radar.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import outspin.mvp.radar.R;
import outspin.mvp.radar.api.JSONBuilder;
import outspin.mvp.radar.data.DummieData;
import outspin.mvp.radar.data.Macros;
import outspin.mvp.radar.models.Notification;
import outspin.mvp.radar.ui.radar_inside.RadarInsideFragment;

public class NotificationsDialog extends BottomSheetDialogFragment {
    private final Context context;
    private ArrayList<Notification> notifications;
    private View view;

    public NotificationsDialog(Context parent) {
        this.context = parent;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState) ;
        view = View.inflate(getContext(), R.layout.dialog_notifications, null);
        dialog.setContentView(view);

        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setPeekHeight(2500); // height of the first state

        PopulateNotifications populateNotifications = new PopulateNotifications(this);
        populateNotifications.execute();

        return dialog;
    }

    public class PopulateNotifications extends AsyncTask<Void, Void, JSONObject> {
        HttpURLConnection urlConnection = null;
        String jsonString = null;
        NotificationsDialog parent;

        PopulateNotifications(NotificationsDialog parent) {
            this.parent = parent;
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            JSONObject jsonData = null;
            try {
                URL url = new URL("http://92.222.10.201:62126/notifications?id=21");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoInput(false);

                int statusCode = urlConnection.getResponseCode();

                if(statusCode == Macros.SERVER_STATUS_OK) {
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
                    StringBuilder stringBuilder = new StringBuilder();

                    String responseLineFromAPI;
                    while ((responseLineFromAPI = bufferedReader.readLine()) != null)
                        stringBuilder.append(responseLineFromAPI).append("\n");

                    bufferedReader.close();

                    jsonString = stringBuilder.toString();
                    jsonData = JSONBuilder.JSONfromString(jsonString).getJSONObject("data");
                } else {
                    Log.d("STATUS CODE", "NOT OK");
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return jsonData;
        }

        @Override
        protected void onPostExecute(JSONObject jsonNotificationData) {
            super.onPostExecute(jsonNotificationData);

            notifications = new ArrayList<>();

            try {
                JSONArray notificationsArrayJson = jsonNotificationData.getJSONArray("list");
                int numOfNotifications = notificationsArrayJson.length();
                for(int i = 0; i < numOfNotifications; i++) {
                    notifications.add( new Notification(notificationsArrayJson.getJSONObject(i)) );
                    notifications.get(i).setMessage(String.valueOf(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RecyclerView recyclerView = view.findViewById(R.id.rv_interactions);
            InteractionsAdapter interactionsAdapter = new InteractionsAdapter(notifications);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(interactionsAdapter);
        }
    }
}