package com.example.epuser.pickcontacts.network;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by ADMIN on 7/18/2016.
 */
public class VolleyStringRequest {

    public static void request(Context context, int method, String url, final OnStringResponse onResponse) {
        StringRequest stringRequest = new StringRequest(method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onResponse.responseReceived(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onResponse.errorReceived(error.networkResponse.statusCode, error.getMessage());
            }
        });

        VolleyRequestQueue.getInstance(context).addToRequestQueue(stringRequest);
    }

    public interface OnStringResponse {
        void responseReceived(String resonse);

        void errorReceived(int code, String message);
    }
}
