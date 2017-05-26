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
    private boolean mShowingBack = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_login);

        btnlog =(Button)findViewById(R.id.btnlog);
        btnreg=(Button)findViewById(R.id.btnreg);
        forgotPassword = (TextView)findViewById(R.id.txtfrgt);
       // checkserver = (Button)findViewById(R.id.checkserver);

        btnreg.setOnClickListener(this);
        btnlog.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
//        checkserver.setOnClickListener(this);


        isLoggedIn();

    }



    @Override
    public void onClick(View v)
    {
        if (v==btnlog)
        {
            flipLogin();
//            FragmentManager manager = getFragmentManager();
//            LoginFragment loginFragment = new LoginFragment();
//            FragmentTransaction transaction = manager.beginTransaction();
//            transaction.replace(R.id.lgcontainer, loginFragment, "loginFragment");
//            transaction.commit();
        }
        if (v==btnreg)
        {
            flipRegister();
//            FragmentManager manager = getFragmentManager();
//            RegisterFragment registerFragment = new RegisterFragment();
//            FragmentTransaction transaction = manager.beginTransaction();
//            transaction.replace(R.id.lgcontainer, registerFragment, "registerFragment");
//            transaction.commit();
        }
        if(v == forgotPassword)
        {

            FragmentManager manager = getFragmentManager();
            ForgotPasswordFragment forgotPasswordFragment = new ForgotPasswordFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.lgcontainer, forgotPasswordFragment, "forgotPasswordFragment");
            transaction.commit();


        }
//        if(v==checkserver){
//
//            new LoginPage.CheckServerAsyncTask().execute();
//
//        }
    }
//    private class LoginViaServer extends AsyncTask<Void,Void,String>
//    {
//
//        @Override
//        protected String doInBackground(Void... params) {
//
//
//
//            String savedMobileNo = null;
//            String savedPassword = null;
//            String serverLoginUrl = "http://api.androidhive.info/contacts/";
//            SharedPreferences loginCheck = getSharedPreferences("userData",MODE_PRIVATE);
//            savedMobileNo=loginCheck.getString("mobileNo",null);
//            savedPassword = loginCheck.getString("password",null);
//            JSONObject data = new JSONObject();
//            try {
//                data.put("HEADER", "FJGH");
//                JSONObject data1 = new JSONObject();
//                data1.put("mobNo","1234567890");
//                data1.put("reqAmount", 200);
//                data.put("DATA", data1);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            //create the required json object
//            String resultFromBackend = Utils.makeRequestNGetResponse("POST",serverLoginUrl,data.toString());
//            return resultFromBackend;
//

//        }
//        @Override
//        protected void onPostExecute(String result)
//        {
//            startActivity(new Intent(LoginPage.this,MainActivity.class));
//            try {
//                JSONObject jsonResult = new JSONObject(result);
//                //parse json
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }

//    private class CheckServerAsyncTask extends AsyncTask<Void,Void ,String>
//    {
//
//        @Override
//        protected String doInBackground(Void... params) {
//
//            try {
//                SocketAddress sockaddr = new InetSocketAddress("http://google.com", 80);
//                // Create an unbound socket
//                Socket sock = new Socket();
//
    private void flipRegister(){
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }

        // Flip to the back.

       // mShowingBack = true;

        // Create and commit a new fragment transaction that adds the fragment for
        // the back of the card, uses custom animations, and is part of the fragment
        // manager's back stack.

        getFragmentManager()
                .beginTransaction()

                // Replace the default fragment animations with animator resources
                // representing rotations when switching to the back of the card, as
                // well as animator resources representing rotations when flipping
                // back to the front (e.g. when the system Back button is pressed).
                .setCustomAnimations(
                        R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)

                // Replace any fragments currently in the container view with a
                // fragment representing the next page (indicated by the
                // just-incremented currentPage variable).
                .replace(R.id.lgcontainer, new RegisterFragment())

                // Add this transaction to the back stack, allowing users to press
                // Back to get to the front of the card.
               // .addToBackStack(null)

                // Commit the transaction.
                .commit();
    }

    private void flipLogin() {


        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }

        // Flip to the back.

       // mShowingBack = true;

        // Create and commit a new fragment transaction that adds the fragment for
        // the back of the card, uses custom animations, and is part of the fragment
        // manager's back stack.

        getFragmentManager()
                .beginTransaction()

                // Replace the default fragment animations with animator resources
                // representing rotations when switching to the back of the card, as
                // well as animator resources representing rotations when flipping
                // back to the front (e.g. when the system Back button is pressed).
                .setCustomAnimations(
                        R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)

                // Replace any fragments currently in the container view with a
                // fragment representing the next page (indicated by the
                // just-incremented currentPage variable).
                .replace(R.id.lgcontainer, new LoginFragment())

                // Add this transaction to the back stack, allowing users to press
                // Back to get to the front of the card.
                // .addToBackStack(null)

                // Commit the transaction.
                .commit();
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
            FirstFragment firstFragment = new FirstFragment();
            FragmentTransaction transactionlogin = managerlogin.beginTransaction();
            transactionlogin.replace(R.id.lgcontainer, firstFragment, "firstFragment");
            transactionlogin.commit();

        }


    }

}

