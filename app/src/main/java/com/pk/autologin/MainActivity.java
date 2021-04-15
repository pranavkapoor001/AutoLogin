package com.pk.autologin;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pk.autologin.services.AutoLoginService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent autoLoginService = new Intent(this, AutoLoginService.class);
        startService(autoLoginService);
    }
}