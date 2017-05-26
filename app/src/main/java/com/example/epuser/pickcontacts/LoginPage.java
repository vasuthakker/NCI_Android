package com.example.epuser.pickcontacts;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epuser.pickcontacts.common.AppConstants;
import com.example.epuser.pickcontacts.common.URLGenerator;
import com.example.epuser.pickcontacts.common.Utils;
import com.example.epuser.pickcontacts.exceptions.InternetNotAvailableException;
import com.example.epuser.pickcontacts.network.VolleyJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;

/**
 * Created by epuser on 5/19/2017.
 */

public class LoginPage extends AppCompatActivity implements View.OnClickListener {

    public Button btnlog, btnreg, checkserver;
    private TextView forgotPassword;
    private static final String TAG = "LoginPage";
    private boolean mShowingBack = false;

    SharedPreferences loginCheck;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_login);

        btnlog = (Button) findViewById(R.id.btnlog);
        btnreg = (Button) findViewById(R.id.btnreg);
        forgotPassword = (TextView) findViewById(R.id.txtfrgt);

        btnreg.setOnClickListener(this);
        btnlog.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
    }


    @Override
    public void onStart() {
        super.onStart();

        loginCheck = getSharedPreferences(AppConstants.KEY_SHARED_PREF, Context.MODE_PRIVATE);
        if (loginCheck.getBoolean(AppConstants.IS_LOGGED_IN, false))
            flipRegister();
        else
            flipLogin();

    }

    @Override
    public void onClick(View v) {
        if (v == btnlog) {
            flipLogin();
//            FragmentManager manager = getFragmentManager();
//            LoginFragment loginFragment = new LoginFragment();
//            FragmentTransaction transaction = manager.beginTransaction();
//            transaction.replace(R.id.lgcontainer, loginFragment, "loginFragment");
//            transaction.commit();
        }
        if (v == btnreg) {
            flipRegister();
//            FragmentManager manager = getFragmentManager();
//            RegisterFragment registerFragment = new RegisterFragment();
//            FragmentTransaction transaction = manager.beginTransaction();
//            transaction.replace(R.id.lgcontainer, registerFragment, "registerFragment");
//            transaction.commit();
        }
        if (v == forgotPassword) {

            FragmentManager manager = getFragmentManager();
            ForgotPasswordFragment forgotPasswordFragment = new ForgotPasswordFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.lgcontainer, forgotPasswordFragment, "forgotPasswordFragment");
            transaction.commit();


        }

    }

    //        if(v==checkserver){
//
//            new LoginPage.CheckServerAsyncTask().execute();
//
//        }
    private void login() {
        // TODO: 5/26/2017  Check on login button click

        int mobile = loginCheck.getInt("mobileNumber", 0);
        try {
            JSONObject requestJson = new JSONObject();
            requestJson.put("mobile", mobile);
            VolleyJsonRequest.request(this, Utils.generateURL(URLGenerator.URL_LOGIN), requestJson, loginResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(this, getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    VolleyJsonRequest.OnJsonResponse loginResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {


        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(LoginPage.this, message);
        }
    };

    private void flipRegister() {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out,
                        R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out
                       )

                .replace(R.id.lgcontainer, new RegisterFragment())
                .commit();
    }

    private void flipLogin() {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)
                .replace(R.id.lgcontainer, new LoginFragment())
                .commit();
    }

    protected void isLoggedIn() {

        if (loginCheck.getBoolean("isLogin", false)) {

        } else {
            startActivity(new Intent(LoginPage.this, EnterPinActivity.class));
            //flipLogin();

        }


    }

}

