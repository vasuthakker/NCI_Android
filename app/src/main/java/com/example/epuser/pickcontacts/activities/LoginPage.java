package com.example.epuser.pickcontacts.activities;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.common.AppConstants;
import com.example.epuser.pickcontacts.common.Preference;
import com.example.epuser.pickcontacts.fragments.LoginFragment;
import com.example.epuser.pickcontacts.fragments.RegisterFragment;

/**
 * Created by epuser on 5/19/2017.
 */

public class LoginPage extends AppCompatActivity  {

    private static final String TAG = "LoginPage";
    private boolean mShowingBack = false;
    private FragmentManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_login);

        manager = getSupportFragmentManager();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Preference.getBooleanPreference(LoginPage.this, AppConstants.IS_LOGGED_IN))
            changeFragment(new LoginFragment());
        else
            //startActivity(new Intent(this,HomeActivity.class));
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

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
}

