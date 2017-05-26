package com.example.epuser.pickcontacts;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.epuser.pickcontacts.common.URLGenerator;
import com.example.epuser.pickcontacts.common.Utils;
import com.example.epuser.pickcontacts.exceptions.InternetNotAvailableException;
import com.example.epuser.pickcontacts.network.VolleyJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by epuser on 5/20/2017.
 */

public class LoginFragment extends Fragment  {

    private EditText enterPin, edtPassword;
    private Button btnLogin;
    private static final String TAG = "LoginFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        return view;
    }

    private void init() {
        enterPin = (EditText) getActivity().findViewById(R.id.enterpin);
        edtPassword = (EditText) getActivity().findViewById(R.id.lgpswrd);
        btnLogin = (Button) getActivity().findViewById(R.id.btnlogg);

    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
                startActivity(new Intent(getActivity(),MainActivity.class));
            }
        });
    }

    private void login() {
        String mobile = enterPin.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            enterPin.setError(getString(R.string.enter_mobile));
            return;
        } else if (mobile.length() < 10) {
            enterPin.setError(getString(R.string.enter_valid_mobile));
            return;
        } else if (mobile.length() > 9)
            mobile = mobile.substring(mobile.length() - 10);

        try {
            JSONObject requestJson = new JSONObject();
            requestJson.put("mobile", mobile);
            VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_LOGIN), requestJson, loginResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private VolleyJsonRequest.OnJsonResponse loginResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {

        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
        }
    };


//    @Override
//    public void onClick(View v) {
//        if (v == btnLogin) {
//            if (CheckNetwork.isInternetAvailable(getActivity())) {
//                String phone = edtMobile.getText().toString().replaceAll("\\s+", "");
//                if (phone.length() > 9) {
//                    phone = phone.substring(phone.length() - 10);
//                } else {
//                    edtMobile.setText(null);
//                    edtPassword.setText(null);
//                    Toast.makeText(getActivity(), "Enter a valid Phone Number", Toast.LENGTH_LONG).show();
//                    return;
//                }
//
//                String loginPassword = edtPassword.getText().toString();
//                // String url = "http://192.168.10.93:8080/epcore/balance/Loader";
//                String url = "http://api.androidhive.info/contacts/";
//
//                JSONObject data = new JSONObject();
//                try {
//                    data.put("HEADER", "FJGH");
//                    JSONObject data1 = new JSONObject();
//                    data1.put("mobNo", phone);
//                    data1.put("reqAmount", 200);
//                    data.put("DATA", data1);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//                JsonObjectRequest jsonRequest = new JsonObjectRequest(url, data, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getActivity(), MainActivity.class);
//                        startActivity(intent);
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//                );
//                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//                requestQueue.add(jsonRequest);
//
//
//                //create Jsonobject to be sent to backend
//                //new LoginFragment.LoginAsyncTask().execute("POST", url, data.toString());
//            } else {
//                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
//            }
//        }
//    }



}




