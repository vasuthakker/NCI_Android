package com.example.epuser.pickcontacts;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.epuser.pickcontacts.common.URLGenerator;
import com.example.epuser.pickcontacts.common.Utils;
import com.example.epuser.pickcontacts.exceptions.InternetNotAvailableException;
import com.example.epuser.pickcontacts.network.VolleyJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by epuser on 5/20/2017.
 */

public class RegisterFragment extends Fragment {

    private EditText regacnt,regphn;
    private Button  btnRegister;
    private static final String TAG = "LoginFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
        View view= inflater.inflate(R.layout.fragment_register,container,false);
      //  regacnt =(EditText)view.findViewById(R.id.regacnt);



        return  view;

    }

    private void init() {
        regphn=(EditText)getActivity().findViewById(R.id.regphn);

        btnRegister=(Button) getActivity().findViewById(R.id.btnregister);



    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();

                FragmentManager manager = getFragmentManager();
                SendOTP sendOTP = new SendOTP();

                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.lgcontainer, sendOTP, "sendOTP");
                transaction.commit();
            }
        });
    }

    private void Register() {

        String mobile = regphn.getText().toString();
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
            requestJson.put("mobile", mobile);
            VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_LOGIN), requestJson, registerResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private VolleyJsonRequest.OnJsonResponse registerResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
//            FragmentManager manager = getFragmentManager();
//            SendOTP sendOTP = new SendOTP();
//
//            FragmentTransaction transaction = manager.beginTransaction();
//            transaction.replace(R.id.lgcontainer, sendOTP, "sendOTP");
//            transaction.commit();
        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
        }
    };
    }


//    @Override
//    public void onClick(View v) {
//        if(v==btnregister){
//            if(CheckNetwork.isInternetAvailable(getActivity())){
//                FragmentManager manager = getFragmentManager();
//                SendOTP sendOTP = new SendOTP();
//
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.replace(R.id.lgcontainer, sendOTP, "sendOTP");
//                transaction.commit();
//
//
//            }
//            else {
//
//                Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_SHORT).show();
//
//
//
//            }
//
//
//
//        }
//
//    }

