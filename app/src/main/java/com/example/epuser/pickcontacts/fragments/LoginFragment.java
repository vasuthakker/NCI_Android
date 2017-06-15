package com.example.epuser.pickcontacts.fragments;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epuser.pickcontacts.activities.DataAdapter;
import com.example.epuser.pickcontacts.activities.HomeActivity;
import com.example.epuser.pickcontacts.activities.LoginPage;
import com.example.epuser.pickcontacts.activities.MainActivity;
import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.activities.PatientAdapter;
import com.example.epuser.pickcontacts.common.AppConstants;
import com.example.epuser.pickcontacts.common.Preference;
import com.example.epuser.pickcontacts.common.URLGenerator;
import com.example.epuser.pickcontacts.common.Utils;
import com.example.epuser.pickcontacts.entities.PatientID;
import com.example.epuser.pickcontacts.entities.Transactions;
import com.example.epuser.pickcontacts.exceptions.InternetNotAvailableException;
import com.example.epuser.pickcontacts.network.VolleyJsonRequest;
import com.goodiebag.pinview.Pinview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by epuser on 5/20/2017.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText edtPassword;
    private Pinview  enterPin;
    private Button btnLogin;
    private TextView forgot_pin_TV,registerTV;
    private static final String TAG = "LoginFragment";
    private LoginPage loginActivity;
    private PatientAdapter pAdapter;
    private List<PatientID> DataList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    private void init() {
        enterPin = (Pinview) getActivity().findViewById(R.id.enterpin);

        btnLogin = (Button) getActivity().findViewById(R.id.btnlogg);
        forgot_pin_TV = (TextView) getActivity().findViewById(R.id.forgot_pin_TV);
        registerTV = (TextView)getActivity().findViewById(R.id.registerTV);
        btnLogin.setOnClickListener(this);
        forgot_pin_TV.setOnClickListener(this);
        registerTV.setOnClickListener(this);

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
        enterPin.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {
                login();
            }
        });

    }




    @Override
    public void onClick(View v) {
        if (v == forgot_pin_TV) {
            loginActivity.changeFragment(new ForgotPasswordFragment());

        }
        else if (v == registerTV)
        {
            loginActivity.changeFragment(new RegisterFragment());
        }

    }

    private void login() {
        String pin = enterPin.getValue();

        try {
            JSONObject requestJson = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            requestJson.put("HEADER", jsonObject1);
            jsonObject2.put("mPin", pin);
            jsonObject2.put("mobileNumber", Preference.getStringPreference(getActivity(), AppConstants.MOBILE_NUMBER));
            requestJson.put("DATA", jsonObject2);

            VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_LOGIN), requestJson, LoginCheckResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private VolleyJsonRequest.OnJsonResponse LoginCheckResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
            Preference.savePreference(getActivity(),AppConstants.IS_LOGGED_IN,true);
           // Intent intent = new Intent(getActivity(), HomeActivity.class);
           // startActivity(intent);
          //  getActivity().finish();
            getPatientID();
        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
        }
    };


    private void getPatientID() {
        try {
            JSONObject requestJson = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            requestJson.put("HEADER", jsonObject1);
           // jsonObject2.put("mobileNumber", Preference.getStringPreference(getActivity(), AppConstants.MOBILE_NUMBER));
            jsonObject2.put("mobileNumber","9164024091");
            requestJson.put("DATA", jsonObject2);

            VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_PATIENTID), requestJson, PatientGetResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private VolleyJsonRequest.OnJsonResponse PatientGetResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
            try {
                JSONArray data = jsonObj.getJSONArray("DATA");
                showPatientID(data);
            } catch (JSONException e) {
                Log.e(TAG, "", e);
            }

        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
        }
    };



    private void showPatientID( JSONArray jsonObject) {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog_for_patientid,null);
        dialogBuilder.setView(dialogView);

        RecyclerView recycler_patient = (RecyclerView) dialogView.findViewById(R.id.recycler_patient);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycler_patient.setLayoutManager(mLayoutManager);
        recycler_patient.setItemAnimator(new DefaultItemAnimator());
        pAdapter = new PatientAdapter(DataList);
        recycler_patient.setAdapter(pAdapter);
        dialogBuilder.setTitle("PatientID");
        dialogBuilder.setMessage("Please choose your PatientID ");



        List<PatientID> DataList = new ArrayList<>();
        PatientID patientId;
        try {
            Log.v(TAG, "History is:" + jsonObject.toString());
            for (int i = 0; i < jsonObject.length(); i++) {
                patientId = new PatientID();
                JSONObject c = jsonObject.getJSONObject(i);
                patientId.setPatientId(c.getString("hmipatientId"));
                patientId.setPatientnName(c.getString("firstname"));
                DataList.add(patientId);
            }
        } catch (JSONException e) {
            Log.e(TAG, "some error occurred", e);
        }

        final AlertDialog b = dialogBuilder.create();
        b.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {


            }
        });

        b.show();
    }





}







