package com.example.epuser.pickcontacts.fragments;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsMessage;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.epuser.pickcontacts.R;
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
 * Created by epuser on 5/22/2017.
 */

public class SendOTP extends Fragment implements View.OnClickListener {
    private static final String TAG = "SendOTP";
    private static final int PERMISSION_READSMS =1 ;
    private EditText enterOTP;
    private Button submitOTPBtn;
    private LoginPage loginActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.otp_send,container,false);

        return  view;
    }
    @Override
    public void onStart(){
        super.onStart();

        askForSMSPermission();



        enterOTP =(EditText)getActivity().findViewById(R.id.enterOTP_ET);
        enterOTP.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        submitOTPBtn=(Button)getActivity().findViewById(R.id.submit_OTP_btn);
        submitOTPBtn.setOnClickListener(this);


    }



    private void askForSMSPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS},
                    PERMISSION_READSMS);

        } else
            registerReceiver();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READSMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    registerReceiver();

                } else {
                    Toast.makeText(getActivity(), getString(R.string.sms_permission), Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivity = (LoginPage) context;
    }

    @Override
    public void onClick(View v) {
        if(v==submitOTPBtn){

             submitOTP();
        }

    }

    private void submitOTP()
    {
        String otp = enterOTP.getText().toString();
        if (TextUtils.isEmpty(otp)) {
            enterOTP.setError(getString(R.string.enter_pin));
            return;
        } else if (otp.length() != 6) {
            enterOTP.setError(getString(R.string.enter_valid_pin));
            return;
        }

        try {
            JSONObject requestJson = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            requestJson.put("HEADER", jsonObject1);
            jsonObject2.put("mobileNumber", Preference.getStringPreference(getActivity(),AppConstants.TEMP_MOBILE_NUMBER));
            jsonObject2.put("otp",otp);
            requestJson.put("DATA", jsonObject2);

            VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_OTP_VERIFICATION), requestJson, otpResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private VolleyJsonRequest.OnJsonResponse otpResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
            loginActivity.changeFragment(new CreatePinFragment());
        }
        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
        }
    };

    private void registerReceiver() {
        IntentFilter iFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
       BroadcastReceiver smsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle data = intent.getExtras();
                Object[] pdus = (Object[]) data.get("pdus");

                for (int i = 0; i < pdus.length; i++) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

                    String sender = smsMessage.getDisplayOriginatingAddress();
                    Log.v(TAG, "Sender: "+sender);
                    String messageBody = smsMessage.getMessageBody();
                    Log.v(TAG, "messageBody: " + messageBody);
                    if(sender.equals(AppConstants.EP_SMS_HOST)) {
                        enterOTP.setText(messageBody.substring(0,6));
                    }
                }
            }
        };

        getActivity().registerReceiver(smsReceiver,iFilter);
    }
}

