package com.example.epuser.pickcontacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    private EditText enterOTP;
    private Button submitOTPBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.otp_send,container,false);

        return  view;
    }
    @Override
    public void onStart(){
        super.onStart();
        enterOTP =(EditText)getActivity().findViewById(R.id.enterOTP_ET);
        submitOTPBtn=(Button)getActivity().findViewById(R.id.submit_OTP_btn);
        submitOTPBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==submitOTPBtn){
             submitOTP();
        }

    }

    private void submitOTP()
    {
        String mobile = enterOTP.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            enterOTP.setError(getString(R.string.enter_mobile));
            return;
        } else if (mobile.length() < 10) {
            enterOTP.setError(getString(R.string.enter_valid_mobile));
            return;
        } else if (mobile.length() > 10)
            mobile = mobile.substring(mobile.length() - 10);

        try {
            JSONObject requestJson = new JSONObject();
            requestJson.put("mobile", mobile);
            VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_LOGIN), requestJson, loginResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private VolleyJsonRequest.OnJsonResponse loginResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {


        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
        }
    };
}

