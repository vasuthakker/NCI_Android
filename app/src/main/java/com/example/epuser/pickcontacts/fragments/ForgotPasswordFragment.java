package com.example.epuser.pickcontacts.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import static android.R.id.message;


public class ForgotPasswordFragment extends Fragment implements View.OnClickListener {
    private EditText answersecurity;
    private TextView seqQnTV;
    private Button btnsubmitanswer;

    private static final String TAG = "ForgotPasswordFragment";
    private LoginPage loginActivity;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_forgot,container,false);
        return view;

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivity = (LoginPage) context;
    }


    @Override
    public void onStart() {
        super.onStart();
        init();
        getsecurityqn();
    }



    private void init() {
        answersecurity=(EditText)getActivity().findViewById(R.id.security_answer);
        seqQnTV = (TextView) getActivity().findViewById(R.id.security_question_show);
        btnsubmitanswer=(Button)getActivity().findViewById(R.id.btnanssub);
        btnsubmitanswer.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == btnsubmitanswer){

            checkanswer();

        }
    }

    private void checkanswer() {
        String answer = answersecurity.getText().toString();


        try {
            JSONObject requestJson = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            requestJson.put("HEADER", jsonObject1);
            jsonObject2.put("Security_Ans", answer);
            jsonObject2.put("mobileNumber", Preference.getStringPreference(getActivity(), AppConstants.MOBILE_NUMBER));
            requestJson.put("DATA", jsonObject2);
            // TODO: 5/31/2017 generate url using urlgenerator
            VolleyJsonRequest.request(getActivity(), "http://192.168.10.65:8080/epnci/forgotPinSecAnsVer", requestJson, CheckAnsResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private VolleyJsonRequest.OnJsonResponse CheckAnsResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {


           loginActivity.changeFragment(new ForgotSetPinFragment());


        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
        }
    };


    private void getsecurityqn() {
        try {
            JSONObject requestJson = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            requestJson.put("HEADER", jsonObject1);
            jsonObject2.put("mobileNumber", Preference.getStringPreference(getActivity(), AppConstants.MOBILE_NUMBER));
            requestJson.put("DATA", jsonObject2);

            VolleyJsonRequest.request(getActivity(), "http://192.168.10.65:8080/epnci/forgotPinSecurityQues", requestJson, getsecqnResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }

    }

    private VolleyJsonRequest.OnJsonResponse getsecqnResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
            try {
                String securityqn = jsonObj.getString("Security_Ques");
                seqQnTV.setText(securityqn);

            } catch (JSONException e) {
                Log.e(TAG,"",e);
            }


        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
        }
    };

}
