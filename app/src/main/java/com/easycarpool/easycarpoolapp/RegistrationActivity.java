package com.easycarpool.easycarpoolapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        String [] compArray = getResources().getStringArray(R.array.company_arrays);
        Spinner companySpinner = (Spinner)findViewById(R.id.company_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, compArray);
        companySpinner.setAdapter(adapter);
        //comment
    }
}
