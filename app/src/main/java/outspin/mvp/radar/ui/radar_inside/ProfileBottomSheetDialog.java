package outspin.mvp.radar.ui.radar_inside;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import outspin.mvp.radar.R;

public class ProfileBottomSheetDialog extends BottomSheetDialogFragment  {

    private final Context context;
    public ProfileBottomSheetDialog(Context parent) {
        this.context = parent;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        final View view = View.inflate(getContext(), R.layout.profile_bottom_sheet_dialog, null);
        dialog.setContentView(view);

        return dialog;
    }
}
