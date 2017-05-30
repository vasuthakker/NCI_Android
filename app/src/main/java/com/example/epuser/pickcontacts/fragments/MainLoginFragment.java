package com.example.epuser.pickcontacts.fragments;

/**
 * Created by epuser on 5/29/2017.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.epuser.pickcontacts.activities.MainActivity;
import com.example.epuser.pickcontacts.common.AppConstants;
import com.example.epuser.pickcontacts.common.Preference;
import com.example.epuser.pickcontacts.common.URLGenerator;
import com.example.epuser.pickcontacts.common.Utils;
import com.example.epuser.pickcontacts.exceptions.InternetNotAvailableException;
import com.example.epuser.pickcontacts.network.VolleyJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainLoginFragment extends Fragment {
    private EditText phoneNumberET,enterPinET;
    private Button loginBtn;
    private static final String TAG = "MainLoginFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_login, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }



    private void init() {
        phoneNumberET = (EditText)getActivity().findViewById(R.id.phone_number_ET);
        phoneNumberET.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        enterPinET = (EditText)getActivity().findViewById(R.id.enter_pin_ET);
        loginBtn = (Button)getActivity().findViewById(R.id.login_btn);
    }

    private void login() {
        String mobile = phoneNumberET.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            phoneNumberET.setError(getString(R.string.enter_mobile));
            return;
        } else if (mobile.length() < 10) {
            phoneNumberET.setError(getString(R.string.enter_valid_mobile));
            return;
        } else if (mobile.length() > 9)
            mobile = mobile.substring(mobile.length() - 10);
        String pin = enterPinET.getText().toString();

        if (pin.length() != 4) {
            enterPinET.setError(getString(R.string.enter_valid_pin));
            return;
        }
        Preference.savePreference(getActivity(),AppConstants.MOBILE_NUMBER,mobile);

        try {
            JSONObject requestJson = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            requestJson.put("HEADER", jsonObject1);
            jsonObject2.put("mPin", pin);
            jsonObject2.put("mobileNumber", Preference.getStringPreference(getActivity(), AppConstants.MOBILE_NUMBER));
            requestJson.put("DATA", jsonObject2);

            VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_PIN_VERIFICATION), requestJson, mainLoginResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }


    }
    private VolleyJsonRequest.OnJsonResponse mainLoginResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {

            startActivity(new Intent(getActivity(), MainActivity.class));
        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
        }
    };
}
