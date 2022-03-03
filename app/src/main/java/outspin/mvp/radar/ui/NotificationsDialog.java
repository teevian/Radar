package outspin.mvp.radar.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import outspin.mvp.radar.R;
import outspin.mvp.radar.data.DummieData;
import outspin.mvp.radar.models.Interaction;

public class NotificationsDialog extends BottomSheetDialogFragment {
    private final Context context;
    public NotificationsDialog(Context parent) {
        this.context = parent;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState) ;
        final View view = View.inflate(getContext(), R.layout.dialog_notifications, null);
        dialog.setContentView(view);

        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setPeekHeight(1500); // height of the first state

        ArrayList<Interaction> interactions = new ArrayList<>(DummieData.DUMMY_INTERACTIONS_FULL);

        RecyclerView recyclerView = view.findViewById(R.id.rv_interactions);
        InteractionsAdapter interactionsAdapter = new InteractionsAdapter(interactions);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(interactionsAdapter);

        return dialog;
    }
}