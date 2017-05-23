package com.example.epuser.pickcontacts;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by epuser on 5/19/2017.
 */

public class LoginPage extends AppCompatActivity implements View.OnClickListener {

    private Button btnlog,btnreg,checkserver;
    private TextView txtfrgt;
    boolean exists =false;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_login);


        btnlog =(Button)findViewById(R.id.btnlog);
        btnreg=(Button)findViewById(R.id.btnreg);
        txtfrgt=(TextView)findViewById(R.id.txtfrgt) ;
        checkserver=(Button)findViewById(R.id.checkserver);

        btnreg.setOnClickListener(this);
        btnlog.setOnClickListener(this);
        txtfrgt.setOnClickListener(this);
        checkserver.setOnClickListener(this);



        FragmentManager manager = getFragmentManager();
        LoginFragment loginFragment = new LoginFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.lgcontainer, loginFragment, "loginFragment");
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        if (v==btnlog){
            FragmentManager manager = getFragmentManager();
            LoginFragment loginFragment = new LoginFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.lgcontainer, loginFragment, "loginFragment");
            transaction.commit();
        }
        if (v==btnreg){
            FragmentManager manager = getFragmentManager();
            RegisterFragment registerFragment = new RegisterFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.lgcontainer, registerFragment, "registerFragment");
            transaction.commit();
        }
        if (v==txtfrgt){
            btnlog.setVisibility(View.GONE);
            btnreg.setVisibility(View.GONE);
            FragmentManager manager = getFragmentManager();
            ForgotPasswordFragment forgotPasswordFragment = new ForgotPasswordFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.lgcontainer, forgotPasswordFragment, "forgotPasswordFragment");
            transaction.commit();
        }

        if(v==checkserver){

            new LoginPage.LoginAsyncTask().execute();

        }
    }


    private class LoginAsyncTask extends AsyncTask<Void,Void ,String>
    {

        @Override
        protected String doInBackground(Void... params) {

            try {
                SocketAddress sockaddr = new InetSocketAddress("http://google.com", 80);
                // Create an unbound socket
                Socket sock = new Socket();

                // This method will block no more than timeoutMs.
                // If the timeout occurs, SocketTimeoutException is thrown.
                int timeoutMs = 2000;   // 2 seconds
                sock.connect(sockaddr, timeoutMs);
                exists = true;
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



//            Intent intent= new Intent(LoginPage.this,MainActivity.class);
//            startActivity(intent);
//            finish();
//            Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
            //if success update sharedpreferences, fetch data , go to homepage
            //else try again logging in

        }
    }
}
