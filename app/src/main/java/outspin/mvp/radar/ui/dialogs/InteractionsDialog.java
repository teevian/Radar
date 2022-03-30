package outspin.mvp.radar.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import outspin.mvp.radar.R;
import outspin.mvp.radar.api.APIErrorResponse;
import outspin.mvp.radar.api.APIHandler;
import outspin.mvp.radar.api.JSONParser;
import outspin.mvp.radar.models.Interaction;
import outspin.mvp.radar.ui.adapters.InteractionsAdapter;

public class InteractionsDialog extends BottomSheetDialogFragment implements APIHandler.APIConnectionCallback {
    private final Context context;
    private View view;
    APIHandler.QueryAPI getInteractions;

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

        getInteractions = new APIHandler.QueryAPI(this);
        getInteractions.execute();

        return dialog;
    }

    @Override
    public void onSuccess(JSONObject json) {
        // TODO i've got a feeling.... that tonight's gonna be a good night
        List<Interaction> interactions = null;

        try {
            Log.d("RRRRRRRRRRRRR", json.toString());
            interactions = JSONParser.interactionsFromJSON(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = view.findViewById(R.id.rv_interactions);
        InteractionsAdapter interactionsAdapter = new InteractionsAdapter(interactions);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(interactionsAdapter);
    }

    @Override
    public void onFailure(APIErrorResponse jsonError) {

    }

    @Override
    public APIHandler.APIConnectionBundle getAPIConnectionBundle() {
        HashMap<String, String> queries = new HashMap<>();
        queries.put("id", "20");

        return new APIHandler.APIConnectionBundle("GET", new String[]{"notifications"}, queries, null);
    }

    @Override
    public void onStop() {
        super.onStop();
        getInteractions.cancel(true);  // to prevent memory leaks
    }

}