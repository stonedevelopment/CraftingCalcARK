package com.gmail.jaredstone1982.craftingcalcark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        finish();
    }
}
