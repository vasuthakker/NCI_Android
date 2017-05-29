package com.example.epuser.pickcontacts.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.fragments.RegisterFragment;
import com.example.epuser.pickcontacts.common.AppConstants;
import com.example.epuser.pickcontacts.common.Preference;
import com.example.epuser.pickcontacts.fragments.LoginFragment;

/**
 * Created by epuser on 5/19/2017.
 */

public class LoginPage extends AppCompatActivity  {

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

      init();

        manager = getSupportFragmentManager();
    }

    private void init() {
        btnlog = (Button) findViewById(R.id.btnlog);
        btnreg = (Button) findViewById(R.id.btnreg);
        forgotPassword = (TextView) findViewById(R.id.txtfrgt);

    }


    @Override
    public void onStart() {
        super.onStart();
        if (Preference.getBooleanPreference(LoginPage.this, AppConstants.IS_LOGGED_IN))
            changeFragment(new LoginFragment());
        else
            changeFragment(new RegisterFragment());
    }
    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment tmpFragment = manager.findFragmentById(R.id.lgcontainer);
        if (tmpFragment != null)
            transaction.replace(R.id.lgcontainer, fragment);
        else
            transaction.add(R.id.lgcontainer, fragment);
        transaction.commitAllowingStateLoss();
    }
}

