package outspin.mvp.radar.ui.components;

import android.content.Context;
import android.util.Pair;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Map;

import outspin.mvp.radar.R;
import outspin.mvp.radar.databinding.ButtonValidatorBinding;

public class ButtonValidator extends androidx.appcompat.widget.AppCompatButton {
    private ButtonValidatorBinding binding;

    public ButtonValidator(@NonNull Context context,
                           TextView editText,
                           Map<String, Pair<TextView, String>> validationMap) {
        super(context);
    }

    public ButtonValidator(@NonNull Context context) {
        super(context);
    }

    private boolean canSubmit() {
        // check if validation map are all ok
        return false;
    }

    private boolean validate(boolean condition) {
        return true;
    }

    private void stateChange() {
        this.setBackgroundTintList(getContext().getColorStateList(
                canSubmit() ? R.color.green : R.color.black
        ));
    }
}
