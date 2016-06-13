package com.easycarpool.easycarpoolapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class HomeScreenActivity extends AppCompatActivity {
    private static final String TAG = "easycarpool.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Typeface rageItalicFont = Typeface.createFromAsset(getAssets(), "fonts/Rage_Italic.ttf");
        TextView homeScreenText = (TextView) findViewById(R.id.homeScreenText);
        homeScreenText.setTypeface(rageItalicFont);
        final String dataStore = getResources().getString(R.string.data_store);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp =
                        getSharedPreferences(dataStore,
                                Context.MODE_PRIVATE);
                String ifOTPScreenActivated  = sp.getString("OTPScreenActivated", null);
                if(ifOTPScreenActivated!=null && ifOTPScreenActivated.equalsIgnoreCase("true")){
                    Log.i(TAG, "Navigating to Verification Page");
                    Intent i = new Intent(getBaseContext(),VerificationActivity.class);
                    startActivity(i);
                }else {
                    Log.i(TAG, "Navigating to Registeration Page");
                    Intent i = new Intent(getBaseContext(), RegistrationActivity.class);
                    startActivity(i);
                }
            }
        }, 2000);

        //comment more
    }
}
