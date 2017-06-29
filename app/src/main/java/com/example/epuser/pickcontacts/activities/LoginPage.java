package com.example.epuser.pickcontacts.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.common.AppConstants;
import com.example.epuser.pickcontacts.common.Preference;
import com.example.epuser.pickcontacts.fragments.LoginFragment;
import com.example.epuser.pickcontacts.fragments.RegisterFragment;
import com.example.epuser.pickcontacts.FAQpackage.CustomPagerAdapter;

/**
 * Created by epuser on 5/19/2017.
 */

public class LoginPage extends AppCompatActivity  {

    private static final String TAG = "LoginPage";
    private boolean mShowingBack = false;
    private FragmentManager manager;
    private boolean doubleBackToExitPressedOnce = false;
    private int[] images =
            {
                    R.drawable.slider1,
                    R.drawable.slider1,
                    R.drawable.slider1

            };
    private CustomPagerAdapter mCustomPagerAdapter;
    private ViewPager mViewPager;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_login);
        manager = getSupportFragmentManager();

        mCustomPagerAdapter = new CustomPagerAdapter(this , images);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);

        Runnable imageSwitcher = new Runnable() {
            @Override
            public void run() {
                mViewPager.setCurrentItem((mViewPager.getCurrentItem() + 1)%images.length, true);
                handler.postDelayed(this, 3000);
            }
        };
        handler.post(imageSwitcher);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Preference.getBooleanPreference(LoginPage.this, AppConstants.IS_LOGGED_IN))
            changeFragment(new LoginFragment());
        else
            //startActivity(new Intent(this,HistoryFragment.class));
            changeFragment(new RegisterFragment());
    }

    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment tmpFragment = manager.findFragmentById(R.id.lgcontainer);
        if (tmpFragment != null)
            transaction.replace(R.id.lgcontainer, fragment);
        else
            transaction.add(R.id.lgcontainer, fragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        //else {
//            super.onBackPressed();
//        }
        else if(!doubleBackToExitPressedOnce){
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press one more time to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        }

        else  if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }


    }
}

