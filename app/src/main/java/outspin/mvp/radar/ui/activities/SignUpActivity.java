package outspin.mvp.radar.ui.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import outspin.mvp.radar.R;
import outspin.mvp.radar.api.APIErrorResponse;
import outspin.mvp.radar.api.APIHandler;
import outspin.mvp.radar.api.JSONParser;
import outspin.mvp.radar.databinding.ActivitySignUpBinding;
import outspin.mvp.radar.utils.Validator;

public class SignUpActivity extends AppCompatActivity implements APIHandler.APIConnectionCallback {
    private ActivitySignUpBinding binding;

    private final int REQUEST_CODE_PHOTO = 1;

    private boolean passwordIsOK    = false;
    private boolean phoneIsOK       = false;
    private boolean firstNameIsOK   = false;
    private boolean lastNameIsOK    = false;
    private boolean photoIsOK       = true;

    private Bitmap photo;
    private String photoEncoded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.etFirstName.requestFocus();
    }

    @Override
    protected void onStart() {
        super.onStart();

        binding.etFirstName.addTextChangedListener(new Validator.TextValidator(binding.etFirstName) {
            @Override
            public void validate(TextView textView, String firstName) {
                firstNameIsOK = Validator.matches(firstName, Validator.VALIDATION_KEY.NAME);
                submitButtonStateChange();
            }
        });

        binding.etLastName.addTextChangedListener(new Validator.TextValidator(binding.etLastName) {
            @Override
            public void validate(TextView textView, String lastName) {
                lastNameIsOK = Validator.matches(lastName, Validator.VALIDATION_KEY.NAME);
                submitButtonStateChange();
            }
        });

        binding.etLoginPhone.addTextChangedListener(new Validator.TextValidator(binding.etLoginPhone) {
            @Override
            public void validate(TextView textView, String phone) {
                phoneIsOK = Validator.matches(phone, Validator.VALIDATION_KEY.PHONE);
                submitButtonStateChange();
            }
        });

        binding.etLoginPassword.addTextChangedListener(new Validator.TextValidator(binding.etLoginPassword) {
            @Override
            public void validate(TextView textView, String password) {
                passwordIsOK = Validator.matches(password, Validator.VALIDATION_KEY.PASSWORD);
                submitButtonStateChange();
            }
        });

        binding.ivEditPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_PHOTO);
            }
        });

        binding.btSubmit.setOnClickListener(view -> {
            if(canSubmit()) {
                APIHandler.QueryAPI loginTask = new APIHandler.QueryAPI(this);
                loginTask.execute();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PHOTO && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = Objects.requireNonNull(data).getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                binding.ivEditPhoto.setImageBitmap(bitmap);

                ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, byteOutputStream);
                byte[] imageBytes = byteOutputStream.toByteArray();

                photoEncoded = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                photoIsOK = true;
                submitButtonStateChange();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSuccess(JSONObject jsonResponse) {
        // TODO create user from JSON and pass it to intent
        Toast.makeText(this, "USER CREATED", Toast.LENGTH_SHORT).show();

        APIHandler.ChangePhoto changePhotoTask = new APIHandler.ChangePhoto();
        changePhotoTask.execute(APIHandler.API_HOSTNAME + "/users/" + 1);
    }

    @Override
    public void onFailure(APIErrorResponse error) {
        // TODO create user from JSON and pass it to intent
        Toast.makeText(this, "NOT CREATED!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public APIHandler.APIConnectionBundle getAPIConnectionBundle() {
        String countryCode  = binding.ccpCountryCode.getSelectedCountryCode();
        String phone        = Objects.requireNonNull(binding.etLoginPhone.getText()).toString();
        String password     = Objects.requireNonNull(binding.etLoginPassword.getText()).toString();
        String firstName    = Objects.requireNonNull(binding.etFirstName.getText()).toString();
        String lastName     = Objects.requireNonNull(binding.etLastName.getText()).toString();

        JSONObject registerJSON = null;
        URL url = null;
        try {
            registerJSON = JSONParser.registerJSONFromCredentials(
                    countryCode, phone, password, firstName, lastName, photoEncoded );
            url = new URL(APIHandler.API_HOSTNAME + "/users/register");
        } catch (JSONException | MalformedURLException e) {
            e.printStackTrace();
        }

        return new APIHandler.APIConnectionBundle("POST", url, registerJSON);
    }

    private boolean canSubmit() { return phoneIsOK && passwordIsOK && firstNameIsOK && lastNameIsOK && photoIsOK; }

    private void submitButtonStateChange() {
        binding.btSubmit.setBackgroundTintList(getBaseContext().getColorStateList(
                canSubmit() ? R.color.green : R.color.black
        ));
    }
}