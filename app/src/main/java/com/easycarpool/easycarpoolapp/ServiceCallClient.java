package com.easycarpool.easycarpoolapp;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by ranjas on 6/8/2016.
 */
public class ServiceCallClient extends IntentService{
    private final String USER_AGENT = "Mozilla/5.0";
    private static final String SERVICE_NAME = "serviceName";
    private static final String PARAMS = "serviceParams";
    private static final String RESPONSE_STRING = "response";
    private static final String TAG = "easycarpool.com";

    public ServiceCallClient() {
        super("ServiceCallClient");
    }


    public String sendGet(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        Log.i(TAG,"Sending 'GET' request to URL : " + url);
        Log.i(TAG,"Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        return response.toString();

    }
    public String sendPost(String url,String params) throws Exception {
        Log.i(TAG,"inside sendPost");
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        //String urlParameters = "firstName=ranjit&password=qwerty&age=27&username=ranjit_behura&email=ranjitjitu@gmail.com&lastName=behura&gender=Male&phoneNumber=7795817409&company=Infosys";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(params);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        Log.i(TAG,"Sending 'POST' request to URL : " + url);
        Log.i(TAG,"Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        return response.toString();

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG,"inside onHandleIntent");
        String serviceName = intent.getStringExtra(SERVICE_NAME);
        String params = intent.getStringExtra(PARAMS);
        String url = getResources().getString(R.string.service_url)+serviceName;
        try {
            String response = sendPost(url, params);
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(Intent.ACTION_SEND);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            broadcastIntent.putExtra(RESPONSE_STRING, response);
            sendBroadcast(broadcastIntent);
        }catch (Exception e){
            Log.i(TAG,"onHandleIntent"+e);
        }
    }
}
