package com.example.epuser.pickcontacts.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.fragments.AboutUs;
import com.example.epuser.pickcontacts.fragments.ContactUsFragment;
import com.example.epuser.pickcontacts.fragments.FeedbackFragment;
import com.example.epuser.pickcontacts.fragments.HistoryFragment;
import com.example.epuser.pickcontacts.fragments.MainFragment;
import com.example.epuser.pickcontacts.fragments.MyAccountFragment;
import com.example.epuser.pickcontacts.FAQpackage.FaqFragment;

public class MainNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager manager;
    private boolean doubleBackToExitPressedOnce = false;
    private String TAG= "activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);

        manager = getSupportFragmentManager();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {

                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(
                        getCurrentFocus().getWindowToken(),
                        0
                );
                super.onDrawerOpened(drawerView);

            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle(R.string.title_my_account);
        changeFragment(new MyAccountFragment());
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_change_pin) {
            Intent intent = new Intent(MainNavigationActivity.this, ChangePin.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            getSupportActionBar().setTitle("HOME");
            changeFragment(new MainFragment());

        } else if (id == R.id.nav_trans_history) {
            getSupportActionBar().setTitle("Transaction History");
           changeFragment(new HistoryFragment());

        } else if (id == R.id.nav_my_account) {
            getSupportActionBar().setTitle(R.string.title_my_account);
            changeFragment(new MyAccountFragment());

        } else if (id == R.id.nav_faq) {
            getSupportActionBar().setTitle(R.string.title_faqs);
            changeFragment( new FaqFragment());

        } else if (id == R.id.nav_contact_us) {
            getSupportActionBar().setTitle(getString(R.string.title_contact_us));
            changeFragment(new ContactUsFragment());

        }else if(id==R.id.nav_feedback){
            getSupportActionBar().setTitle(R.string.title_feedback);
            changeFragment(new FeedbackFragment());
        }
        else if (id == R.id.nav_change_pin) {
            startActivity(new Intent(MainNavigationActivity.this,ChangePin.class));
        }else if(id==R.id.nav_about_us){
            getSupportActionBar().setTitle(R.string.title_about_us);
            changeFragment(new AboutUs());
        }

        else if (id == R.id.nav_logout) {
            Intent intent = new Intent(MainNavigationActivity.this, LoginPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment tmpFragment = manager.findFragmentById(R.id.content_frame);
        if (tmpFragment != null)
            transaction.replace(R.id.content_frame, fragment);
        else
            transaction.add(R.id.content_frame, fragment);
        transaction.commitAllowingStateLoss();
    }
}
