package com.example.epuser.pickcontacts.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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


public class ForgotPasswordFragment extends Fragment implements View.OnClickListener {
    private EditText answersecurity,edtsecurityanswer;
    private Button btnsubmitanswer;
    //private TextView showquestion;
    //private Spinner security_questions_spinner;

    //private TextView textview6;


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
    }

    private void init() {
        answersecurity=(EditText)getActivity().findViewById(R.id.security_answer);
        btnsubmitanswer=(Button)getActivity().findViewById(R.id.btnanssub);
       // showquestion =(TextView)getActivity().findViewById(R.id.security_question_show);
        btnsubmitanswer.setOnClickListener(this);
       // security_questions_spinner = (Spinner)getActivity().findViewById(R.id.security_question_spinner);
       // textview6=(TextView)getActivity().findViewById(R.id.textView6);
        edtsecurityanswer=(EditText)getActivity().findViewById(R.id.security_answer_ET);
    }

    @Override
    public void onClick(View v) {
        if(v == btnsubmitanswer){
//            FragmentManager manager = getFragmentManager();
//            SendOTP sendOTP = new SendOTP();
//            Bundle args = new Bundle();
//            args.putString("YourKey", "LoginPage");
//            FragmentTransaction transaction = manager.beginTransaction();
//            sendOTP.setArguments(args);
//            transaction.replace(R.id.lgcontainer, sendOTP, "sendOTP");
//            transaction.commit();
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
            jsonObject2.put("securityanswer", answer);
            jsonObject2.put("mobileNumber", Preference.getStringPreference(getActivity(), AppConstants.MOBILE_NUMBER));
            requestJson.put("DATA", jsonObject2);

            VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_SECURITY_ANS_VERIFICATION), requestJson, CheckAnsResp, true);
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

}
