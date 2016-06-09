package com.easycarpool.easycarpoolapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class VerificationActivity extends AppCompatActivity {
    private static final String TAG = "easycarpool.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        Log.i(TAG,"Verification Page Launched");
    }
}
