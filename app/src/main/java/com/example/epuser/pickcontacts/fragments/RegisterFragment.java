package com.example.epuser.pickcontacts.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.activities.LoginPage;
import com.example.epuser.pickcontacts.common.AppConstants;
import com.example.epuser.pickcontacts.common.Preference;
import com.example.epuser.pickcontacts.common.URLGenerator;
import com.example.epuser.pickcontacts.common.Utils;
import com.example.epuser.pickcontacts.exceptions.InternetNotAvailableException;
import com.example.epuser.pickcontacts.network.VolleyJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by epuser on 5/20/2017.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener{

    private EditText regacnt, regphn;
    private TextView loginTV;
    private Button btnRegister;
    private static final String TAG = "RegisterFragment";
    private LoginPage loginActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivity = (LoginPage) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    private void init() {
        regphn = (EditText) getActivity().findViewById(R.id.regphn);
        regphn.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        btnRegister = (Button) getActivity().findViewById(R.id.btnregister);
        loginTV = (TextView)getActivity().findViewById(R.id.loginTV);

        btnRegister.setOnClickListener(this);
        loginTV.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        Preference.savePreference(getActivity(),AppConstants.IS_LOGGED_IN,false);

    }
    @Override
    public void onClick(View v) {
        if(v ==btnRegister)
        {
            checkIfRegistered();
        }
        else if (v ==loginTV)
        {

            loginActivity.changeFragment(new MainLoginFragment());
        }

    }

    private void checkIfRegistered() {
        String mobile = regphn.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            regphn.setError(getString(R.string.enter_mobile));
            return;
        } else if (mobile.length() < 10) {
            regphn.setError(getString(R.string.enter_valid_mobile));
            return;
        } else if (mobile.length() > 9)
            mobile = mobile.substring(mobile.length() - 10);
        try {
            JSONObject requestJson = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            requestJson.put("HEADER", jsonObject1);
            jsonObject2.put("mobileNumber", mobile);

            jsonObject2.put(getString(R.string.Order_id),System.currentTimeMillis());
            requestJson.put("DATA", jsonObject2);

            VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_REGISTRATION_CHECK), requestJson, isRegisteredResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }

    }
    private VolleyJsonRequest.OnJsonResponse isRegisteredResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
            Register();

        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
        }
    };

    private void Register() {
        String mobile = regphn.getText().toString();
//        if (TextUtils.isEmpty(mobile)) {
//            regphn.setError(getString(R.string.enter_mobile));
//            return;
//        } else if (mobile.length() < 10) {
//            regphn.setError(getString(R.string.enter_valid_mobile));
//            return;
//        } else if (mobile.length() > 9)
//            mobile = mobile.substring(mobile.length() - 10);
        Preference.savePreference(getActivity(),AppConstants.MOBILE_NUMBER,mobile);

        try {
            JSONObject requestJson = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            requestJson.put("HEADER", jsonObject1);
            jsonObject2.put("mobileNumber", mobile);

            jsonObject2.put("udid",LoginPage.getDeviceId(getActivity()));
            requestJson.put("DATA", jsonObject2);

            VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_VERIFY_USER), requestJson, registerResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private VolleyJsonRequest.OnJsonResponse registerResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
            loginActivity.changeFragment(new SendOTP());

        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
        }
    };


}

