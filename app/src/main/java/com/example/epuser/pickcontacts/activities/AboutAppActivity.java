package com.example.epuser.pickcontacts.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.common.AppConstants;
import com.example.epuser.pickcontacts.fragments.AboutUs;
import com.example.epuser.pickcontacts.fragments.ForgotPasswordFragment;
import com.example.epuser.pickcontacts.fragments.RegisterFragment;
import com.example.epuser.pickcontacts.recyler.FaqFragment;

import static com.example.epuser.pickcontacts.R.id.forgot_pin_TV;
import static com.example.epuser.pickcontacts.R.id.registerTV;

public class AboutAppActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView aboutUsImg , faqsImg , appTourImg , contactUsImg , offersImg;
    private FragmentManager manager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        init();
        openFirstFragment();
    }

    private void init() {

        manager = getSupportFragmentManager();
        aboutUsImg = (ImageView)findViewById(R.id.ls_Img_about);
        faqsImg = (ImageView)findViewById(R.id.ls_img_faq);
        appTourImg = (ImageView)findViewById(R.id.ls_img_tour);
        contactUsImg = (ImageView)findViewById(R.id.ls_img_contact_us);
        offersImg = (ImageView)findViewById(R.id.ls_img_offers);
        toolbar = (Toolbar)findViewById(R.id.about_app_toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        aboutUsImg.setOnClickListener(this);
        faqsImg.setOnClickListener(this);
        appTourImg.setOnClickListener(this);
        contactUsImg.setOnClickListener(this);
        offersImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v ==aboutUsImg){
            toolbar.setTitle(getString(R.string.title_about_us));
            changeFragment(new AboutUs());
        }
        else if (v ==faqsImg){
            toolbar.setTitle(getString(R.string.title_faqs));
            changeFragment( new FaqFragment());
        }
        else if (v == appTourImg){


        }
        else if (v ==contactUsImg){


        }
        else if (v == offersImg){

        }

    }

    private void openFirstFragment() {

        String fragmentId = getIntent().getStringExtra(AppConstants.FRAGMENT_ID);
        if (fragmentId.equals(getString(R.string.title_about_us))) {
            toolbar.setTitle(getString(R.string.title_about_us));
            changeFragment(new AboutUs());


        } else if (fragmentId.equals(getString(R.string.title_faqs))) {
            toolbar.setTitle(getString(R.string.title_faqs));
            changeFragment(new FaqFragment());

        } else if (fragmentId.equals("tour")) {
        } else if (fragmentId.equals("contact")) {
        } else if (fragmentId.equals("offers")) {
        }
    }

    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment tmpFragment = manager.findFragmentById(R.id.fragment_container);
        if (tmpFragment != null)
            transaction.replace(R.id.fragment_container, fragment);
        else
            transaction.add(R.id.fragment_container, fragment);
        transaction.commitAllowingStateLoss();
    }
}
