package com.example.epuser.pickcontacts.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.activities.LoginPage;
import com.example.epuser.pickcontacts.activities.MainActivity;
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

    private EditText regacnt, regphn;
    private TextView loginTV;
    private Button btnRegister;
    private static final String TAG = "RegisterFragment";
    private LoginPage loginActivity;
    private String mobile = null;

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
    }

    @Override
    public void onStart() {
        super.onStart();
        init();


    }
    @Override
    public void onClick(View v) {
        if(v ==btnRegister)
        {
            checkIfRegistered();
        }
        else if (v ==loginTV)
        {
            if (Preference.getBooleanPreference(getActivity(),AppConstants.IS_LOGGED_IN))
                loginActivity.changeFragment(new LoginFragment());
            else
            {
                showOTPDialog();
            }


        }

    }

    private void checkIfRegistered() {
         mobile = regphn.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            regphn.setError(getString(R.string.enter_mobile));
            return;
        } else if (mobile.length() < 10) {
            regphn.setError(getString(R.string.enter_valid_mobile));
            return;
        } else if (mobile.length() > 9)
            mobile = mobile.substring(mobile.length() - 10);
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
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
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
       //  mobile = regphn.getText().toString();
//        if (TextUtils.isEmpty(mobile)) {
//            regphn.setError(getString(R.string.enter_mobile));
//            return;
//        } else if (mobile.length() < 10) {
//            regphn.setError(getString(R.string.enter_valid_mobile));
//            return;
//        } else if (mobile.length() > 9)
//            mobile = mobile.substring(mobile.length() - 10);


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
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
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
        // mobile = regphn.getText().toString();
//        if (TextUtils.isEmpty(mobile)) {
//            regphn.setError(getString(R.string.enter_mobile));
//            return;
//        } else if (mobile.length() < 10) {
//            regphn.setError(getString(R.string.enter_valid_mobile));
//            return;
//        } else if (mobile.length() > 9)
//            mobile = mobile.substring(mobile.length() - 10);


        try {
            JSONObject requestJson = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            requestJson.put("HEADER", jsonObject1);
            jsonObject2.put("mobileNumber", mobile);

            //jsonObject2.put("udid",LoginPage.getDeviceId(getActivity()));
            requestJson.put("DATA", jsonObject2);

            VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_OTP), requestJson, generateOTPResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private VolleyJsonRequest.OnJsonResponse generateOTPResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
            Preference.savePreference(getActivity(),AppConstants.TEMP_MOBILE_NUMBER,mobile);
            loginActivity.changeFragment(new SendOTP());

        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
        }
    };

    public void showOTPDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog_for_login, null);
        dialogBuilder.setView(dialogView);

        final EditText mobileET = (EditText) dialogView.findViewById(R.id.phoneNumberET);
        mobileET.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        dialogBuilder.setTitle("LOGIN");
        dialogBuilder.setMessage("Please enter your Phone Number ");
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
                            mobileET.setError(getString(R.string.enter_valid_mobile));



                    }
                });
            }
        });

        b.show();
    }


}

