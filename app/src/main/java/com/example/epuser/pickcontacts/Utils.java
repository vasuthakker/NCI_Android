package com.example.epuser.pickcontacts;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Bittu Kumar on 18-05-2017.
 */

public class Utils {
    //private static Context context;

    public static String makeRequestNGetResponse(String method, String url, String requestBody) {
        String response = null;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
// add request header
            con.setRequestMethod(method);
// con.setRequestProperty(“User-Agent”, USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-Type","application/json");

            String cred = "admin:vbs518";
            con.setRequestProperty("Authorization", "Basic " + Base64.encodeToString(cred.getBytes(), Base64.DEFAULT));

// Send post request
            if (method.equals("POST"))
                con.setDoOutput(true);

            if (requestBody != null && !requestBody.isEmpty()) {
                DataOutputStream wr = new DataOutputStream(
                        con.getOutputStream());
                wr.writeBytes(requestBody);
                wr.flush();
                wr.close();
            }

            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuilder responseBuffer = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    responseBuffer.append(inputLine);
                }
                in.close();
// print result
                response = responseBuffer.toString();
            }

        } catch (MalformedURLException e) {

            Log.e("errror", "MalformedURLException", e);
        } catch (IOException e) {
            Log.e("Errorrr", "IOException", e);
            //Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        Log.v("result",response);
        return response;
    }

}
