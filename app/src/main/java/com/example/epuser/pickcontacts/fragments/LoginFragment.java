package com.example.epuser.pickcontacts.fragments;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dpizarro.pinview.library.PinView;
import com.example.epuser.pickcontacts.activities.AboutAppActivity;
import com.example.epuser.pickcontacts.activities.LoginPage;
import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.activities.MainNavigationActivity;
import com.example.epuser.pickcontacts.activities.PatientAdapter;
import com.example.epuser.pickcontacts.activities.RecyclerItemClickListener;
import com.example.epuser.pickcontacts.common.AppConstants;
import com.example.epuser.pickcontacts.common.Preference;
import com.example.epuser.pickcontacts.common.URLGenerator;
import com.example.epuser.pickcontacts.common.Utils;
import com.example.epuser.pickcontacts.entities.PatientID;
import com.example.epuser.pickcontacts.exceptions.InternetNotAvailableException;
import com.example.epuser.pickcontacts.network.VolleyJsonRequest;
import com.example.epuser.pickcontacts.FAQpackage.FaqFragment;
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



    private PinView pinView;
    private TextView forgot_pin_TV, registerTV;
    private ImageView aboutUsImg , faqsImg , appTourImg , contactUsImg , offersImg;
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
        pinView = (PinView)getActivity().findViewById(R.id.pinView);
        forgot_pin_TV = (TextView) getActivity().findViewById(R.id.forgot_pin_TV);
        registerTV = (TextView) getActivity().findViewById(R.id.registerTV);

        forgot_pin_TV.setOnClickListener(this);
        registerTV.setOnClickListener(this);

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
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivity = (LoginPage) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        init();

        pinView.setOnCompleteListener(new PinView.OnCompleteListener() {
            @Override
            public void onComplete(boolean completed, final String pinResults) {
                if (completed && pinResults!=null) {
                    if (pinResults.length() ==4) {
                        login(pinResults);
                    }

                }

            }
        });

    }


    @Override
    public void onClick(View v) {
        if (v == forgot_pin_TV) {
            loginActivity.changeFragment(new ForgotPasswordFragment());

        }
        else if (v == registerTV) {
            loginActivity.changeFragment(new RegisterFragment());
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
            intent.putExtra(AppConstants.FRAGMENT_ID,getString(R.string.title_contact_us));
            startActivity(intent);

        }
        else if (v == offersImg){
            Intent intent = new Intent(getActivity(), AboutAppActivity.class);
            intent.putExtra(AppConstants.FRAGMENT_ID,getString(R.string.title_about_us));
            startActivity(intent);

        }

    }

    private void login(String pin) {

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
            Preference.savePreference(getActivity(), AppConstants.IS_LOGGED_IN, true);
//             Intent intent = new Intent(getActivity(), MainNavigationActivity.class);
//             startActivity(intent);
//              getActivity().finish();
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

            jsonObject2.put("mobileNumber", Preference.getStringPreference(getActivity(), AppConstants.MOBILE_NUMBER));
            requestJson.put("DATA", jsonObject2);

            VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_PATIENTID), requestJson, PatientGetResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "getPatientId: JSONException", e);
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


    private void showPatientID(JSONArray jsonObject) {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog_for_patientid, null);
        dialogBuilder.setView(dialogView);

        RecyclerView recycler_patient = (RecyclerView) dialogView.findViewById(R.id.recycler_patient);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycler_patient.setLayoutManager(mLayoutManager);
        recycler_patient.setItemAnimator(new DefaultItemAnimator());
        recycler_patient.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recycler_patient, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        PatientID patientID = DataList.get(position);
                        Preference.savePreference(getActivity(),AppConstants.PATIENT_ID,patientID.getPatientId());
                        String PatientName = patientID.getFirstName() + " " + patientID.getMiddleName() + " " + patientID.getLastName();
                        Preference.savePreference(getActivity(),AppConstants.CURRENT_PATIENT_NAME,PatientName);
                        Intent intent = new Intent(getActivity(), MainNavigationActivity.class);
                        startActivity(intent);
                        getActivity().finish();

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );

        dialogBuilder.setTitle("Patient ID");
        dialogBuilder.setMessage("Please choose your Patient ID ");
        DataList = new ArrayList<>();
        PatientID patientId;
        try {

            for (int i = 0; i < jsonObject.length(); i++) {
                patientId = new PatientID();
                JSONObject c = jsonObject.getJSONObject(i);
                patientId.setPatientId(c.getString("hmipatientId"));
                patientId.setFirstName(c.getString("firstname"));
                if (c.getString("middlename") ==null || c.getString("middlename").equals("") ||c.getString("middlename").equals("null") ){
                    patientId.setMiddleName("");
                }
                else
                {
                    patientId.setMiddleName(c.getString("middlename"));
                }
                if (c.getString("lastname") ==null ||  c.getString("lastname").equals("") ||c.getString("lastname").equals("null")){
                    patientId.setLastName("");
                }
                else
                {
                    patientId.setLastName(c.getString("lastname"));
                }

                DataList.add(patientId);
            }

            pAdapter = new PatientAdapter(DataList);
            recycler_patient.setAdapter(pAdapter);

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







