package com.example.epuser.pickcontacts.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epuser.pickcontacts.activities.LoginPage;
import com.example.epuser.pickcontacts.activities.MainActivity;
import com.example.epuser.pickcontacts.R;
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

public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText enterPin, edtPassword;
    private Button btnLogin;
    private TextView forgot_pin_TV;
    private static final String TAG = "LoginFragment";
    private LoginPage loginActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        return view;
    }

    private void init() {
        enterPin = (EditText) getActivity().findViewById(R.id.enterpin);
        btnLogin = (Button) getActivity().findViewById(R.id.btnlogg);
        forgot_pin_TV = (TextView)getActivity().findViewById(R.id.forgot_pin_TV);
        btnLogin.setOnClickListener(this);
        forgot_pin_TV.setOnClickListener(this);

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivity = (LoginPage) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        init();

    }
    @Override
    public void onClick(View v) {
        if(v==btnLogin)
        {
            login();
        }
        else if(v == forgot_pin_TV)
        {
            loginActivity.changeFragment(new ForgotPasswordFragment());

        }

    }

    private void login() {
        String pin = enterPin.getText().toString();



         if (pin.length() != 4) {
            enterPin.setError(getString(R.string.enter_valid_pin));
            return;
        }

        try {
            JSONObject requestJson = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            requestJson.put("HEADER", jsonObject1);
            jsonObject2.put("mPin", pin);
            jsonObject2.put("mobileNumber",Preference.getStringPreference(getActivity(),AppConstants.MOBILE_NUMBER));
            requestJson.put("DATA", jsonObject2);

            VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_PIN_VERIFICATION), requestJson, LoginCheckResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private VolleyJsonRequest.OnJsonResponse LoginCheckResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
            try {
                String response =jsonObj.getString(AppConstants.KEY_RESP);
                if(response.equals(getString(R.string.request_complete))) {

                    Intent intent =new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
        }
    };



//        if(pin.equals((Preference.getStringPreference(getActivity(), AppConstants.PIN))))
//        {
//            Intent intent = new Intent(getActivity(),MainActivity.class);
//            startActivity(intent);
//            getActivity().finish();
//        }
//        else
//        {
//            enterPin.setError("wrong pin! Try again");
//        }

}







