package com.example.epuser.pickcontacts.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.activities.ChangePin;
import com.example.epuser.pickcontacts.activities.MainNavigationActivity;
import com.example.epuser.pickcontacts.common.AppConstants;
import com.example.epuser.pickcontacts.common.Preference;
import com.example.epuser.pickcontacts.common.URLGenerator;
import com.example.epuser.pickcontacts.common.Utils;
import com.example.epuser.pickcontacts.exceptions.InternetNotAvailableException;
import com.example.epuser.pickcontacts.network.VolleyJsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;


/**
 * Created by epuser on 20-Jun-17.
 */

public class MyAccountFragment extends Fragment implements View.OnClickListener {
   private EditText ETFirstName  , ETMobile , ETEmail ;
   private TextView TVUpdateDetails , TVsave , TVImgName, TVBalance ;
    private MainNavigationActivity mainNavigationActivity;
    private static final String TAG = "MyAccountFragment";


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainNavigationActivity = (MainNavigationActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_account, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        getBalance();

    }



    private void init() {
        TVBalance = (TextView)getActivity().findViewById(R.id.TVMyAccBalance);
        ETFirstName = (EditText)getActivity().findViewById(R.id.ETFirstName);
        ETMobile = (EditText)getActivity().findViewById(R.id.ETMyAccMobile);
        ETEmail = (EditText)getActivity().findViewById(R.id.ETMyAccEmail);
        TVUpdateDetails = (TextView) getActivity().findViewById(R.id.TVUpdateDetails);
        TVsave = (TextView)getActivity().findViewById(R.id.TVMyAccSave);
        TVImgName = (TextView)getActivity().findViewById(R.id.TVMyAccImgName);
        TVUpdateDetails.setOnClickListener(this);
        TVsave.setOnClickListener(this);


        ETFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setImgName();

            }
        });

        ETFirstName.setText(Preference.getStringPreference(getActivity(), AppConstants.CURRENT_PATIENT_NAME));
        ETMobile.setText(Preference.getStringPreference(getActivity(),AppConstants.MOBILE_NUMBER));

        setImgName();

    }



    @Override
    public void onClick(View v) {
        if(v==TVUpdateDetails)
        {

            ETFirstName.setEnabled(true);

            // ETMobile.setEnabled(true);
            ETEmail.setEnabled(true);
            TVsave.setVisibility(View.VISIBLE);
        }
        else if (v == TVsave)
        {

        }

    }
    private void setImgName() {
        String name = ETFirstName.getText().toString();
        String[] fullName = name.split("\\s+");
        String imgName ="";
        for (int i =0;i<Math.min(2,fullName.length);i++)
        {
            if (!(fullName[i].equals("")) && fullName[i]!=null ) {
                imgName = imgName.concat(fullName[i].substring(0, 1));
            }
        }
        imgName =  imgName.toUpperCase();
        TVImgName.setText(imgName);
    }

    private void getBalance() {

        try {
            JSONObject requestJson = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            requestJson.put("HEADER", jsonObject1);

            jsonObject2.put("mobileNumber", Preference.getStringPreference(getActivity(), AppConstants.MOBILE_NUMBER));
            jsonObject2.put("hmipatientId",Preference.getStringPreference(getActivity(),AppConstants.PATIENT_ID));

            requestJson.put("DATA", jsonObject2);

            VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_FETCH_BAlANCE), requestJson, getBalanceResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "getBalance: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private VolleyJsonRequest.OnJsonResponse getBalanceResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
            try {
                JSONObject data = jsonObj.getJSONObject("DATA");
                TVBalance.setText(data.getString("BALANCE"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
        }
    };

}
