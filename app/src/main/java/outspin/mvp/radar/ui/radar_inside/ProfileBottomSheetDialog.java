package outspin.mvp.radar.ui.radar_inside;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import outspin.mvp.radar.R;
import outspin.mvp.radar.databinding.ProfileBottomSheetDialogBinding;
import outspin.mvp.radar.databinding.ProfileDialogPreviewBinding;

public class ProfileBottomSheetDialog extends BottomSheetDialogFragment  {
    Context context;
    ProfileBottomSheetDialogBinding binding;

    public ProfileBottomSheetDialog(Context parent) {
        this.context = parent;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        this.binding = ProfileBottomSheetDialogBinding.inflate(LayoutInflater.from(getContext()));

        //final View view = View.inflate(getContext(), R.layout.profile_bottom_sheet_dialog, null);
        dialog.setContentView(this.binding.getRoot());

        this.binding.profileThumbnail.profileThumbnailPicture.setVisibility(View.INVISIBLE);

        return dialog;
    }
}
