package com.easycarpool.easycarpoolapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Typeface rageItalicFont = Typeface.createFromAsset(getAssets(), "fonts/Rage_Italic.ttf");
        TextView homeScreenText = (TextView) findViewById(R.id.homeScreenText);
        homeScreenText.setTypeface(rageItalicFont);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getBaseContext(),RegistrationActivity.class);
                startActivity(i);
            }
        }, 2000);

        //comment more
    }
}
