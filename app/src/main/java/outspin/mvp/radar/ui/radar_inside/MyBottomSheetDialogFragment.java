package outspin.mvp.radar.ui.radar_inside;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import outspin.mvp.radar.R;
import outspin.mvp.radar.databinding.NotMyProfileBinding;

public class MyBottomSheetDialogFragment extends BottomSheetDialogFragment {

    public MyBottomSheetDialogFragment() {}

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState) ;
        final View view = View.inflate(getContext(), R.layout.not_my_profile, null);
        dialog.setContentView(view);

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setPeekHeight(800); // height of the first state

        return dialog;
    }
}