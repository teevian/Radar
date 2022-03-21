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
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import outspin.mvp.radar.R;
import outspin.mvp.radar.api.APICallBack;
import outspin.mvp.radar.api.APIHandler;
import outspin.mvp.radar.api.JSONParser;
import outspin.mvp.radar.models.Interaction;

public class InteractionsDialog extends BottomSheetDialogFragment implements APICallBack {
    private final Context context;
    private ArrayList<Interaction> notifications;
    private View view;

    public InteractionsDialog(Context parent) {
        this.context = parent;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState) ;
        view = View.inflate(getContext(), R.layout.fragment_interactions_dialog, null);
        dialog.setContentView(view);

        //BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        //bottomSheetBehavior.setPeekHeight(900); // height of the first state TODO HEIGHT

        //PopulateNotifications populateNotifications = new PopulateNotifications(this);
        //populateNotifications.execute();


        APIHandler.QueryAPI getNotifications = new APIHandler.QueryAPI(this);
        getNotifications.execute();

        return dialog;
    }

    @Override
    public void complete(JSONObject json) {
        notifications = new ArrayList<>();

        try {
            // TODO organize to generalize
            JSONArray notificationsArrayJson = json.getJSONObject("data").getJSONArray("interactions");
            int numOfNotifications = notificationsArrayJson.length();
            for(int i = 0; i < numOfNotifications; i++) {
                notifications.add( new Interaction(notificationsArrayJson.getJSONObject(i)) );
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

    @Override
    public APIHandler.APIConnectionBundle getAPIConnectionBundle() {
        HashMap<String, String> queries = new HashMap<>();
        queries.put("id", "20");

        return new APIHandler.APIConnectionBundle("GET", new String[]{"notifications"}, queries, null);
    }

}