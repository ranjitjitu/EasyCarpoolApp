package com.easycarpool.easycarpoolapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Hashtable;

public class RegistrationActivity extends AppCompatActivity {

    //private static PropertyUtil propertyUtil = new PropertyUtil();
    private static ServiceCallClient serviceClient = new ServiceCallClient();
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
    protected void onRegister(View view){
        EditText firstNameComponent = (EditText)findViewById(R.id.FirstNameText);
        EditText lastNameComponent = (EditText)findViewById(R.id.LastNameText);
        EditText userNameComponent = (EditText)findViewById(R.id.UserNameText);
        Spinner companyComponent = (Spinner)findViewById(R.id.company_spinner);
        EditText emailComponent = (EditText)findViewById(R.id.EmailText);
        EditText ageComponent = (EditText)findViewById(R.id.AgeText);
        RadioGroup genderComponent = (RadioGroup)findViewById(R.id.genderRadio);
        RadioButton selectedGender = (RadioButton)findViewById(genderComponent.getCheckedRadioButtonId());
        EditText phoneNumberComponent = (EditText)findViewById(R.id.PhoneText);
        EditText passwordComponent = (EditText)findViewById(R.id.passwordText);
        EditText confirmPasswordComponent = (EditText)findViewById(R.id.confirmPasswordText);
        String companyText = companyComponent.getSelectedItem().toString().trim();
        String firstNameText = firstNameComponent.getText().toString().trim();
        String lastNameText = lastNameComponent.getText().toString().trim();
        String usernameText = userNameComponent.getText().toString().trim();
        String emailText = emailComponent.getText().toString().trim();
        String ageText = ageComponent.getText().toString().trim();
        String genderText = "";
        if(selectedGender!=null) {
            genderText = selectedGender.getText().toString().trim();
        }
        String phoneText = phoneNumberComponent.getText().toString().trim();
        String passwordText = passwordComponent.getText().toString().trim();
        String confirmPasswordText = confirmPasswordComponent.getText().toString().trim();
        if(companyText == null || companyText.equalsIgnoreCase("")){
            showToast("Please Select a Company");
            return;
        }
        if(firstNameText == null || firstNameText.equalsIgnoreCase("")){
            showToast("First Name is Mandatory");
            return;
        }
        if(lastNameText == null || lastNameText.equalsIgnoreCase("")){
            showToast("Last Name is Mandatory");
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
        if(ageText == null || ageText.equalsIgnoreCase("")){
            showToast("Age is Mandatory");
            return;
        }
        if(genderText == null || genderText.equalsIgnoreCase("")){
            showToast("Choose your Gender");
            return;
        }if(!isValidPhone(phoneText) || !(phoneText.length()==10)){
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
       final Hashtable params = new Hashtable();
        params.put("firstName",firstNameText);
        params.put("lastName",lastNameText);
        params.put("username",usernameText);
        params.put("company",companyText);
        params.put("age",ageText);
        params.put("phoneNumber",phoneText);
        params.put("email",emailText);
        params.put("password",passwordText);
        params.put("gender",genderText);
       // PropertyUtil propertyUtil = new PropertyUtil();
       final String url = "http://192.168.1.106:8080/EasyCarpool/easyCarpoolService/registration";
        System.out.println(url);
        System.out.println(params.toString());
        try {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        String response = serviceClient.sendPost(url, params);
                        System.out.println(response);
                    }catch (Exception e){
                        System.out.println("onRegister"+e);
                    }
                }
            }).start();
        }catch (Exception e){
            System.out.println("onRegister"+e);
        }



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
