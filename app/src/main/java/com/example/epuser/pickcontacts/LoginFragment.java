package com.example.epuser.pickcontacts;

import android.app.Fragment;
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

    private EditText edtMobile, edtPassword;
    private Button btnLogin;
    private static final String TAG = "LoginFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        return view;
    }

    private void init() {
        edtMobile = (EditText) getActivity().findViewById(R.id.lgphn);
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
            }
        });
    }

    private void login() {
        String mobile = edtMobile.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            edtMobile.setError(getString(R.string.enter_mobile));
            return;
        } else if (mobile.length() < 10) {
            edtMobile.setError(getString(R.string.enter_valid_mobile));
            return;
        } else if (mobile.length() > 10)
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

}




