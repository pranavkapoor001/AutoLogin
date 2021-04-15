package com.pk.autologin.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pk.autologin.Constants;
import com.pk.autologin.R;
import com.pk.autologin.Utils;
import com.pk.autologin.services.AutoLoginService;

public class CredentialsSaveActivity extends AppCompatActivity implements View.OnClickListener {

    // UI Components
    private Button saveLoginBtn;
    private EditText emailPhoneEt, passwordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credentials_save);

        saveLoginBtn = findViewById(R.id.save_login_button);
        emailPhoneEt = findViewById(R.id.netflix_email_or_phone);
        passwordEt = findViewById(R.id.netflix_password);
        saveLoginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save_login_button) {
            if (!Utils.isAccessibilityGranted(this))
                return;

            if (!TextUtils.isEmpty(emailPhoneEt.getText()) &&
                    !TextUtils.isEmpty(passwordEt.getText())) {
                saveCredentials();
                startAutoLogin();
            } else {
                Toast.makeText(this, "Fill login details", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void saveCredentials() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.EMAIL_PHONE_KEY, emailPhoneEt.getText().toString());
        editor.putString(Constants.PASSWORD_KEY, passwordEt.getText().toString());
        editor.apply();
    }

    public void startAutoLogin() {
        // Launch Netflix app
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClassName(Constants.NETFLIX, "com.netflix.mediaclient.ui.launch.UIWebViewActivity");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ignored) {
            Toast.makeText(this, "Please install Netflix app", Toast.LENGTH_LONG).show();
        }
    }
}
