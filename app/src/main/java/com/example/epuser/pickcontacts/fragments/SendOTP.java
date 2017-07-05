package com.example.epuser.pickcontacts.fragments;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.widget.TextView;
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

import static com.example.epuser.pickcontacts.R.color.colorAccent;


/**
 * Created by epuser on 5/22/2017.
 */

public class SendOTP extends Fragment implements View.OnClickListener {
    private static final String TAG = "SendOTP";
    private static final int PERMISSION_READSMS =1 ;
    private EditText enterOTP;
    private Button submitOTPBtn ,resendOTPBtn;
    private LoginPage loginActivity;
    private TextView otpTimer;
    private CountDownTimer countDownTimer;
    private long otpExpireTimeInSeconds;


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
        otpTimer = (TextView) getActivity().findViewById(R.id.otp_timerTV);
        submitOTPBtn=(Button)getActivity().findViewById(R.id.submit_OTP_btn);
        resendOTPBtn = (Button)getActivity().findViewById(R.id.resend_otp_btn);
        submitOTPBtn.setOnClickListener(this);
        resendOTPBtn.setOnClickListener(this);

        otpExpireTimeInSeconds = 300;

        countDownTimer = new CountDownTimer(otpExpireTimeInSeconds*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                otpTimer.setText("OTP expires in: " + (millisUntilFinished / 1000 -1)  + " Seconds");
                if (isAdded()) {
                    otpTimer.setTextColor(getResources().getColor(R.color.colorDarkGreen));

                }
            }

            public void onFinish() {

                otpTimer.setText(R.string.otp_expired_message);
                if (isAdded())
                {
                    otpTimer.setTextColor(getResources().getColor(R.color.colorRed));
                }

                submitOTPBtn.setVisibility(View.GONE);
                resendOTPBtn.setVisibility(View.VISIBLE);


            }
        };
        countDownTimer.start();


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
        else if (v==resendOTPBtn)
        {
            resendOTP();
        }

    }



    private void submitOTP()
    {
        String otp = enterOTP.getText().toString();
        if (TextUtils.isEmpty(otp)) {
            enterOTP.requestFocus();
            enterOTP.setError(getString(R.string.enter_otp));
            return;
        } else if (otp.length() != 6) {
            enterOTP.requestFocus();
            enterOTP.setError(getString(R.string.enter_valid_OTP));
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
            Log.e(TAG, "submitOTP: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private VolleyJsonRequest.OnJsonResponse otpResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
            Utils.showSuccessToast(getActivity(),"OTP Verified!");
            countDownTimer.cancel();
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
    private void resendOTP() {
        try {
            JSONObject requestJson = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            requestJson.put("HEADER", jsonObject1);
            jsonObject2.put("mobileNumber", Preference.getStringPreference(getActivity(),AppConstants.TEMP_MOBILE_NUMBER));

            //jsonObject2.put("udid",LoginPage.getDeviceId(getActivity()));
            requestJson.put("DATA", jsonObject2);

            VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_OTP), requestJson, resendOTPResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "resendOTP: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private VolleyJsonRequest.OnJsonResponse resendOTPResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
            countDownTimer.start();
            resendOTPBtn.setVisibility(View.GONE);
            submitOTPBtn.setVisibility(View.VISIBLE);
            Utils.showSuccessToast(getActivity(),getString(R.string.otp_send_success_msg));

        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
            otpTimer.setText("");
        }
    };
}

