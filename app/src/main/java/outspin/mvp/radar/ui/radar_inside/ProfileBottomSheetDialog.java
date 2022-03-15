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
import outspin.mvp.radar.models.UserThumb;

public class ProfileBottomSheetDialog extends BottomSheetDialogFragment  {
    private final UserThumb userThumb;

    public ProfileBottomSheetDialog(Context parent, UserThumb userThumb) {
        this.userThumb = userThumb;
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
                .load(userThumb.getPhotoURL())
                .resize(150, 150)
                .centerCrop()
                .into(interactWithUserDialogbinding.profileThumbnail.profileThumbnailPicture);

        return dialog;
    }
}
