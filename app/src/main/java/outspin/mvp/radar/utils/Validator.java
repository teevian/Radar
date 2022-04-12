package outspin.mvp.radar.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Objects;

public class Validator {

    public enum VALIDATION_KEY {
      PASSWORD, NAME, PHONE, ALPHABETIC, NUMERIC, ALPHANUMERIC
    };

    public static final HashMap<VALIDATION_KEY, String> VALIDATION_SCHEMES =
            new HashMap<VALIDATION_KEY, String>() {{
                put(VALIDATION_KEY.PASSWORD, "^(?=.*[a-z])(?=.*\\d).{8,15}$");
                //put(VALIDATION_KEY.PASSWORD, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,15}$");
                put(VALIDATION_KEY.NAME, "[^0-9_!¡?÷?¿+=@#$%ˆ&*()|~<>{};:[\\]]]{2,20}$");
                put(VALIDATION_KEY.PHONE, "^[0-9]{9,15}$");
                put(VALIDATION_KEY.ALPHABETIC, "\\w+");
                put(VALIDATION_KEY.NUMERIC, "\\d+");
                put(VALIDATION_KEY.ALPHANUMERIC, "(?=.*[a-zA-Z])(?=.*[\\\\d]).+");
            }};

    public abstract static class TextValidator implements TextWatcher {
        private final TextView textView;

        public TextValidator(TextView textView) {
            this.textView = textView;
        }

        public abstract void validate(TextView textView, String text);

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            String text = textView.getText().toString();
            validate(textView, text);
        }

        @Override
        public void afterTextChanged(Editable editable) { }
    }

    public static boolean matches(String text, VALIDATION_KEY method) {
        return text.matches(Objects.requireNonNull(VALIDATION_SCHEMES.get(method)));
    }

    @NonNull
    public static String capitalizeString(@NonNull String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public static boolean isPersonNameValid(@NonNull final String name) {
        return name.matches(Objects.requireNonNull(VALIDATION_SCHEMES.get(VALIDATION_KEY.NAME)));
    }

    public static boolean isPasswordValid(@NonNull final String password) {
        return password.matches(Objects.requireNonNull(VALIDATION_SCHEMES.get(VALIDATION_KEY.PASSWORD)));
    }

    public static boolean isPhoneValid(@NonNull final String phone) {
        return phone.matches(Objects.requireNonNull(VALIDATION_SCHEMES.get(VALIDATION_KEY.PHONE)));
    }
}

/*
USE:
editText.addTextChangedListener(new TextValidator(editText) {
    @Override public void validate(TextView textView, String text) {
       // Validation code here
    }
            });
            https://stackoverflow.com/questions/2763022/android-how-can-i-validate-edittext-input?rq=1

    // https://github.com/ragunathjawahar/android-saripaar/blob/master/saripaar/src/main/java/commons/validator/routines/UrlValidator.java

 */
