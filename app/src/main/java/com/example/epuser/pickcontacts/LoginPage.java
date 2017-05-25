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

    private Button btnlog,btnreg,checkserver;
    private TextView  forgotPassword;
    private static final String TAG = "LoginFragment";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_login);

        btnlog =(Button)findViewById(R.id.btnlog);
        btnreg=(Button)findViewById(R.id.btnreg);
        forgotPassword = (TextView)findViewById(R.id.txtfrgt);
        checkserver = (Button)findViewById(R.id.checkserver);

        btnreg.setOnClickListener(this);
        btnlog.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        checkserver.setOnClickListener(this);

        FragmentManager manager = getFragmentManager();
        FirstFragment firstFragment = new FirstFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.lgcontainer, firstFragment, "firstFragment");
        transaction.commit();

        isLoggedIn();

    }

    @Override
    public void onClick(View v)
    {
        if (v==btnlog)
        {
            FragmentManager manager = getFragmentManager();
            LoginFragment loginFragment = new LoginFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.lgcontainer, loginFragment, "loginFragment");
            transaction.commit();
        }
        if (v==btnreg)
        {
            FragmentManager manager = getFragmentManager();
            RegisterFragment registerFragment = new RegisterFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.lgcontainer, registerFragment, "registerFragment");
            transaction.commit();
        }
        if(v == forgotPassword)
        {

            FragmentManager manager = getFragmentManager();
            ForgotPasswordFragment forgotPasswordFragment = new ForgotPasswordFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.lgcontainer, forgotPasswordFragment, "forgotPasswordFragment");
            transaction.commit();


        }
        if(v==checkserver){

            new LoginPage.CheckServerAsyncTask().execute();

        }
    }

    private class CheckServerAsyncTask extends AsyncTask<Void,Void ,String>
    {

        @Override
        protected String doInBackground(Void... params) {

            try {
                SocketAddress sockaddr = new InetSocketAddress("http://google.com", 80);
                // Create an unbound socket
                Socket sock = new Socket();

                int timeoutMs = 2000;   // 2 seconds
                sock.connect(sockaddr, timeoutMs);
                return "server online";
                //
            } catch(IOException e) {
                //Toast.makeText(this,"server offline",Toast.LENGTH_SHORT).show();
                return "server offline";
            }
        }
        @Override
        protected void onPostExecute(String result)
        {
            Toast.makeText(LoginPage.this,result,Toast.LENGTH_SHORT).show();
        }
    }

    protected void isLoggedIn()
    {
        SharedPreferences loginCheck =getSharedPreferences("userData", Context.MODE_PRIVATE);


        if(!(loginCheck.getString("userName",null)==null) && loginCheck.getBoolean("isLogin",false))
        {
            VolleyJsonRequest.OnJsonResponse loginResp = new VolleyJsonRequest.OnJsonResponse() {
                @Override
                public void responseReceived(JSONObject jsonObj) {


                }

                @Override
                public void errorReceived(int code, String message) {
                    Utils.showToast(LoginPage.this, message);
                }
            };
            int mobile = loginCheck.getInt("mobileNumber",0);
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

        else
        {
            FragmentManager managerlogin = getFragmentManager();
            LoginFragment loginFragment = new LoginFragment();
            FragmentTransaction transactionlogin = managerlogin.beginTransaction();
            transactionlogin.replace(R.id.lgcontainer, loginFragment, "loginFragment");
            transactionlogin.commit();

        }


    }

}

