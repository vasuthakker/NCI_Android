package com.example.epuser.pickcontacts.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.inputmethodservice.Keyboard;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.activities.AboutAppActivity;
import com.example.epuser.pickcontacts.activities.LoginPage;
import com.example.epuser.pickcontacts.common.AppConstants;
import com.example.epuser.pickcontacts.common.Preference;
import com.example.epuser.pickcontacts.common.URLGenerator;
import com.example.epuser.pickcontacts.common.Utils;
import com.example.epuser.pickcontacts.exceptions.InternetNotAvailableException;
import com.example.epuser.pickcontacts.network.VolleyJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by epuser on 5/20/2017.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener{

    private static final int PERMISSION_READSMS =1 ;
    private EditText regacnt, regphn;
    private TextView loginTV;
    private Button btnRegister;
    private static final String TAG = "RegisterFragment";
    private LoginPage loginActivity;
    private String mobile = null;
    private ImageView aboutUsImg , faqsImg , appTourImg , contactUsImg , offersImg;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivity = (LoginPage) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    private void init() {
        regphn = (EditText) getActivity().findViewById(R.id.regphn);
        regphn.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        btnRegister = (Button) getActivity().findViewById(R.id.btnregister);
        loginTV = (TextView)getActivity().findViewById(R.id.loginTV);

        btnRegister.setOnClickListener(this);
        loginTV.setOnClickListener(this);

        aboutUsImg = (ImageView) getActivity().findViewById(R.id.ls_Img_about);
        faqsImg = (ImageView)getActivity().findViewById(R.id.ls_img_faq);
        appTourImg = (ImageView)getActivity().findViewById(R.id.ls_img_tour);
        contactUsImg = (ImageView)getActivity().findViewById(R.id.ls_img_contact_us);
        offersImg = (ImageView)getActivity().findViewById(R.id.ls_img_offers);

        aboutUsImg.setOnClickListener(this);
        faqsImg.setOnClickListener(this);
        appTourImg.setOnClickListener(this);
        contactUsImg.setOnClickListener(this);
        offersImg.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        askForSMSPermission();


    }
    @Override
    public void onClick(View v) {
        if(v ==btnRegister)
        {
            mobile = regphn.getText().toString();
            if (TextUtils.isEmpty(mobile)) {

                regphn.requestFocus();
                regphn.setError(getString(R.string.enter_mobile));
                return;
            } else if (mobile.length() < 10) {
                regphn.requestFocus();
                regphn.setError(getString(R.string.enter_valid_mobile));
                return;
            } else if (mobile.length() > 9)
                mobile = mobile.substring(mobile.length() - 10);
            checkIfRegistered();


        }
        else if (v ==loginTV)
        {
            Preference.savePreference(getActivity(),AppConstants.IS_LOGGED_IN,false);
            showLoginDialog();
        }

        else if (v ==aboutUsImg){
            Intent intent = new Intent(getActivity(), AboutAppActivity.class);
            intent.putExtra(AppConstants.FRAGMENT_ID,getString(R.string.title_about_us));
            startActivity(intent);

        }
        else if (v ==faqsImg){
            Intent intent = new Intent(getActivity(), AboutAppActivity.class);
            intent.putExtra(AppConstants.FRAGMENT_ID,getString(R.string.title_faqs));
            startActivity(intent);


        }
        else if (v == appTourImg){
            Intent intent = new Intent(getActivity(), AboutAppActivity.class);
            intent.putExtra(AppConstants.FRAGMENT_ID,getString(R.string.title_about_us));
            startActivity(intent);


        }
        else if (v ==contactUsImg){
            Intent intent = new Intent(getActivity(), AboutAppActivity.class);
            intent.putExtra(AppConstants.FRAGMENT_ID,getString(R.string.title_about_us));
            startActivity(intent);

        }
        else if (v == offersImg){
            Intent intent = new Intent(getActivity(), AboutAppActivity.class);
            intent.putExtra(AppConstants.FRAGMENT_ID,getString(R.string.title_about_us));
            startActivity(intent);

        }

    }

    private void checkIfRegistered() {

        try {
            JSONObject requestJson = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            requestJson.put("HEADER", jsonObject1);
            jsonObject2.put("mobileNumber", mobile);

            jsonObject2.put(getString(R.string.Order_id),System.currentTimeMillis());
            requestJson.put("DATA", jsonObject2);

            VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_REGISTRATION_CHECK), requestJson, isRegisteredResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "checkIfRegistered: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }

    }
    private VolleyJsonRequest.OnJsonResponse isRegisteredResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
            Register();

        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
        }
    };

    private void Register() {

        try {
            JSONObject requestJson = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            requestJson.put("HEADER", jsonObject1);
            jsonObject2.put("mobileNumber", mobile);

            jsonObject2.put("udid",LoginPage.getDeviceId(getActivity()));
            requestJson.put("DATA", jsonObject2);

            VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_VERIFY_USER), requestJson, registerResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "Register: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private VolleyJsonRequest.OnJsonResponse registerResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
            generateOTP();

        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
        }
    };

    private void generateOTP() {

        try {
            JSONObject requestJson = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            requestJson.put("HEADER", jsonObject1);
            jsonObject2.put("mobileNumber", mobile);

            requestJson.put("DATA", jsonObject2);

            VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_OTP), requestJson, generateOTPResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "generateOTP: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private VolleyJsonRequest.OnJsonResponse generateOTPResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
            Preference.savePreference(getActivity(),AppConstants.TEMP_MOBILE_NUMBER,mobile);
            Utils.showSuccessToast(getActivity(),getString(R.string.otp_send_success_msg));
            loginActivity.changeFragment(new SendOTP());

        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
        }
    };

    public void showLoginDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog_for_login, null);
        dialogBuilder.setView(dialogView);

        final EditText mobileET = (EditText) dialogView.findViewById(R.id.phoneNumberET);
        mobileET.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        dialogBuilder.setTitle("LOGIN");
        dialogBuilder.setMessage("Please enter your Questions Number ");
        dialogBuilder.setPositiveButton("SUBMIT", null);

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {


                //pass

            }
        });
        final AlertDialog b = dialogBuilder.create();
        b.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String mobile = mobileET.getText().toString();
                        if (mobile.length() ==10) {
                            Preference.savePreference(getActivity(), AppConstants.MOBILE_NUMBER, mobile);
                            loginActivity.changeFragment(new LoginFragment());
                            b.dismiss();
                        }
                        else
                            mobileET.requestFocus();
                            mobileET.setError(getString(R.string.enter_valid_mobile));
                    }
                });
            }
        });

        b.show();
    }

    private void askForSMSPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS},
                    PERMISSION_READSMS);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READSMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {



                } else {
                    Toast.makeText(getActivity(), getString(R.string.sms_permission), Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

}

