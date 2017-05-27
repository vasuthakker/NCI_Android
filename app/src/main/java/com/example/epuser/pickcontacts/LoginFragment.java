package com.example.epuser.pickcontacts;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        if(pin.equals((Preference.getStringPreference(getActivity(), AppConstants.PIN))))
        {
            Intent intent = new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
        else
        {
            enterPin.setError("wrong pin! Try again");
        }

    }


}




