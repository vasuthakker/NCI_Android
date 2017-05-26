package com.example.epuser.pickcontacts;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
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

    private FragmentManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_login);


    }



    @Override
    public void onStart() {
        super.onStart();
         init();
        if (Preference.getBooleanPreference(LoginPage.this, AppConstants.IS_LOGGED_IN))
            flipLogin();
        else
            flipRegister();

    }

    private void init(){
        btnlog = (Button) findViewById(R.id.btnlog);
        btnreg = (Button) findViewById(R.id.btnreg);
        forgotPassword = (TextView) findViewById(R.id.txtfrgt);

        btnreg.setOnClickListener(this);
        btnlog.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);

        manager = getSupportFragmentManager();
    }

    @Override
    public void onClick(View v) {
        if (v == btnlog) {
            flipLogin();

        }
        if (v == btnreg) {
            flipRegister();

        }
        if (v == forgotPassword) {
            changeFragment(new ForgotPasswordFragment());
        }

    }


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
                        R.animator.card_flip_right_out)

                .replace(R.id.lgcontainer, new RegisterFragment())
                .commitAllowingStateLoss();
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
                .commitAllowingStateLoss();
    }

    protected void isLoggedIn() {

        if (loginCheck.getBoolean("isLogin", false)) {

        } else {
            startActivity(new Intent(LoginPage.this, EnterPinActivity.class));
            //flipLogin();

        }


    }

    public void changeFragment(android.support.v4.app.Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        android.support.v4.app.Fragment tmpFragment = manager.findFragmentById(R.id.lgcontainer);
        if (tmpFragment != null)
            transaction.replace(R.id.lgcontainer, fragment);
        else
            transaction.add(R.id.lgcontainer, fragment);
        transaction.commitAllowingStateLoss();
    }
}

