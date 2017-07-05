package com.example.epuser.pickcontacts.fragments;


import android.content.Context;
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

import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.activities.MainNavigationActivity;
import com.example.epuser.pickcontacts.common.AppConstants;
import com.example.epuser.pickcontacts.common.Preference;
import com.example.epuser.pickcontacts.common.URLGenerator;
import com.example.epuser.pickcontacts.common.Utils;
import com.example.epuser.pickcontacts.exceptions.InternetNotAvailableException;
import com.example.epuser.pickcontacts.network.VolleyJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;
import static com.example.epuser.pickcontacts.R.string.submit;

/**
 * Created by epuser on 6/16/2017.
 */

public class FeedbackFragment extends Fragment{

    private EditText feedbackET;
    private Button submitBtn;
    private MainNavigationActivity mainNavigationActivity;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feedback, container, false);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainNavigationActivity = (MainNavigationActivity) context;

    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });
    }


    private void init() {
        feedbackET = (EditText)getActivity().findViewById(R.id.ET_feedback);
        submitBtn = (Button)getActivity().findViewById(R.id.btn_feedback);
    }

    private void submitFeedback() {
        String feedback = feedbackET.getText().toString();
        if (TextUtils.isEmpty(feedback))
        {
            feedbackET.setError(getString(R.string.empty_feedback_error));
            return;
        }
        try {
            JSONObject requestJson = new JSONObject();
            JSONObject header = new JSONObject();
            JSONObject data = new JSONObject();
            requestJson.put("HEADER", header);
            data.put("hmipatientId",2);
           // data.put("hmipatientId", Preference.getStringPreference(getActivity(), AppConstants.PATIENT_ID));
            data.put("mobileNumber", Preference.getStringPreference(getActivity(),AppConstants.MOBILE_NUMBER));
            data.put("feedback", feedback);

            requestJson.put("DATA", data);

            VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_FEEDBACK), requestJson, feedbackResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }

    }
    private VolleyJsonRequest.OnJsonResponse feedbackResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
            Utils.showSuccessToast(getActivity(),"Feedback Successfully Sent!");
            mainNavigationActivity.getSupportActionBar().setTitle(getString(R.string.title_my_account));
            mainNavigationActivity.changeFragment(new MyAccountFragment());

        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
        }
    };


}
