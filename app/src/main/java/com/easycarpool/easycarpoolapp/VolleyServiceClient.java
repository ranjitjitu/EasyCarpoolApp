package com.easycarpool.easycarpoolapp;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ranjas on 6/9/2016.
 */
public class VolleyServiceClient {
    private static final String TAG = "easycarpool.com";
    private String serviceResponse;

    public String sendPostRequest(String url,Map params) {
        Log.i(TAG, "inside sendPostRequest");
        final Map paramList = params;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "Response from server : "+response);
                        serviceResponse = response;
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("biplab_5", "username");
                params.put("biplab.sh.tripathy@gmail.com", "email");
                params.put("MALE", "gender");
                params.put("GOOGLE INC", "company");
                params.put("123@qwe", "password");
                return params;
            }

        };
        return serviceResponse;
    }
}
