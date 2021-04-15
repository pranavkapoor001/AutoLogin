package com.pk.autologin.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pk.autologin.Constants;
import com.pk.autologin.R;
import com.pk.autologin.services.AutoLoginService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // UI Components
    private Button addCredentialsBtn, mainLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addCredentialsBtn = findViewById(R.id.add_credentials);
        mainLoginBtn = findViewById(R.id.main_login);
        addCredentialsBtn.setOnClickListener(this);
        mainLoginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_credentials:
                startActivity(new Intent(this, CredentialsSaveActivity.class));
                break;
            case R.id.main_login:
                startAutoLogin();
                break;
        }
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
