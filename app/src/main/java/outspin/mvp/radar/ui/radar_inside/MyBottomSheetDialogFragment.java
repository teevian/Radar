package outspin.mvp.radar.ui.radar_inside;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

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
import outspin.mvp.radar.data.Macros;
import outspin.mvp.radar.databinding.NotMyProfileBinding;
import outspin.mvp.radar.models.Interaction;

public class MyBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private Context context;
    public MyBottomSheetDialogFragment(Context parent) {
        this.context = parent;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState) ;
        final View view = View.inflate(getContext(), R.layout.not_my_profile, null);
        dialog.setContentView(view);

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setPeekHeight(800); // height of the first state

        ArrayList<Interaction> interactions = new ArrayList<>(DummieData.DUMMY_INTERACTIONS_FULL);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_interactions);
        InteractionsAdapter interactionsAdapter = new InteractionsAdapter(interactions);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(interactionsAdapter);

        return dialog;
    }
}