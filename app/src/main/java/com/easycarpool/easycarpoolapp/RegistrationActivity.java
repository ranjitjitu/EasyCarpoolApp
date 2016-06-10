package com.easycarpool.easycarpoolapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    //private static PropertyUtil propertyUtil = new PropertyUtil();
    private static final String RESPONSE_STRING = "response";
    private static VolleyServiceClient serviceClient = new VolleyServiceClient();
    private static final String TAG = "easycarpool.com";
    private Boolean exit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        String [] compArray = getResources().getStringArray(R.array.company_arrays);
        Spinner companySpinner = (Spinner)findViewById(R.id.company_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, compArray);
        companySpinner.setAdapter(adapter);
        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");
        TextView registerIcon = (TextView)findViewById(R.id.registrationIcon);
        registerIcon.setTypeface(fontAwesomeFont);
        //comment
    }
    @Override
    public void onBackPressed() {
        if (exit) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
            finish();
            System.exit(0); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
    protected void onRegister(View view){
        EditText userNameComponent = (EditText)findViewById(R.id.UserNameText);
        Spinner companyComponent = (Spinner)findViewById(R.id.company_spinner);
        EditText emailComponent = (EditText)findViewById(R.id.EmailText);
        EditText phoneNumberComponent = (EditText)findViewById(R.id.PhoneText);
        EditText passwordComponent = (EditText)findViewById(R.id.passwordText);
        EditText confirmPasswordComponent = (EditText)findViewById(R.id.confirmPasswordText);
        String companyText = companyComponent.getSelectedItem().toString().trim();
        String usernameText = userNameComponent.getText().toString().trim();
        String emailText = emailComponent.getText().toString().trim();
        String phoneText = phoneNumberComponent.getText().toString().trim();
        String passwordText = passwordComponent.getText().toString().trim();
        String confirmPasswordText = confirmPasswordComponent.getText().toString().trim();
        if(companyText == null || companyText.equalsIgnoreCase("")){
            showToast("Please Select a Company");
            return;
        }
        if(usernameText == null || usernameText.equalsIgnoreCase("")){
            showToast("Username is Mandatory");
            return;
        }
        if(!isValidEmail(emailText)){
            showToast("Email is not valid");
            return;
        }
  if(!isValidPhone(phoneText) || !(phoneText.length()==10)){
            showToast("Phone number is Invalid");
            return;
        }if(passwordText == null || passwordText.equalsIgnoreCase("")){
            showToast("Password is Mandatory");
            return;
        }if(confirmPasswordText == null || confirmPasswordText.equalsIgnoreCase("")){
            showToast("Confirm password is Mandatory");
            return;
        }if(!passwordText.equals(confirmPasswordText)){
            showToast("Password and Confirm Password doesn't match");
            return;
        }
        Map<String,String> params = new HashMap();
        params.put("username",usernameText);
        params.put("company",companyText);
        params.put("phoneNumber",phoneText);
        params.put("email",emailText);
        params.put("password",passwordText);
        String serviceName = "registration";
        String url = getResources().getString(R.string.service_url)+serviceName;
        Log.i(TAG,"service name : "+serviceName);
        Log.i(TAG,"params : "+params.toString());
        final Map paramList = params;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "Response from server : "+response);
                        try {
                            JSONObject responseJson = new JSONObject(response);
                            if (responseJson.optString("Status").toString().equalsIgnoreCase("Success")) {
                                Intent i = new Intent(getBaseContext(), VerificationActivity.class);
                                startActivity(i);
                            } else {
                                showToast(responseJson.get("Message").toString());
                            }
                        }catch (JSONException je){
                            Log.i(TAG,"onRegister : "+je);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                /*Map<String, String> params = new HashMap<String, String>();
                params.put("biplab_5", "username");
                params.put("biplab.sh.tripathy@gmail.com", "email");
                params.put("MALE", "gender");
                params.put("GOOGLE INC", "company");
                params.put("123@qwe", "password");*/
                return paramList;
            }

        };
        queue.add(stringRequest);
    }
    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    private boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    private boolean isValidPhone(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return Patterns.PHONE.matcher(target).matches();
        }
    }
}
