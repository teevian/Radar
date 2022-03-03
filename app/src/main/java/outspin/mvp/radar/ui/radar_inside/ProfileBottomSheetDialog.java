package outspin.mvp.radar.ui.radar_inside;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import outspin.mvp.radar.R;
import outspin.mvp.radar.databinding.DialogInteractWithUserBinding;
import outspin.mvp.radar.models.UserThumbnail;

public class ProfileBottomSheetDialog extends BottomSheetDialogFragment  {
    private final UserThumbnail userThumbnail;

    public ProfileBottomSheetDialog(Context parent, UserThumbnail userThumbnail) {
        this.userThumbnail = userThumbnail;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        DialogInteractWithUserBinding interactWithUserDialogbinding =
                DialogInteractWithUserBinding.inflate(LayoutInflater.from(getContext()));

        dialog.setContentView(interactWithUserDialogbinding.getRoot());

        interactWithUserDialogbinding.tvProfileName.setText("");

        Picasso.with(getContext())
                .load(userThumbnail.getPhotoURL())
                .resize(150, 150)
                .centerCrop()
                .into(interactWithUserDialogbinding.profileThumbnail.profileThumbnailPicture);

        return dialog;
    }
}
