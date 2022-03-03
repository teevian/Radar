package outspin.mvp.radar.ui.radar_inside;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import outspin.mvp.radar.databinding.DialogInteractWithUserBinding;
import outspin.mvp.radar.models.User;

public class ProfileBottomSheetDialog extends BottomSheetDialogFragment  {
    private final Context context;
    private User user;

    public ProfileBottomSheetDialog(Context parent, User user) {
        this.context = parent;
        this.user = user;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        DialogInteractWithUserBinding interactWithUserDialogbinding =
                DialogInteractWithUserBinding.inflate(LayoutInflater.from(getContext()));

        dialog.setContentView(interactWithUserDialogbinding.getRoot());

        Picasso.with(getContext())
                .load(user.getPhotoURL())
                .resize(150, 150)
                .centerCrop()
                .into(interactWithUserDialogbinding.profileThumbnail.profileThumbnailPicture);

        return dialog;
    }
}
