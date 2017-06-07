package com.example.epuser.pickcontacts.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import static android.content.ContentValues.TAG;
import static com.example.epuser.pickcontacts.R.id.confirm_pin_ET;
import static com.example.epuser.pickcontacts.R.id.pin_ET;
import static com.example.epuser.pickcontacts.R.id.regphn;


public class ForgotSetPinFragment extends Fragment  implements View.OnClickListener{
    private EditText pinafterForgot,confirmPinForgot;
    private Button submitpinForgot;
    private LoginPage loginActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivity = (LoginPage) context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_forgot_set_pin,container,false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {

        pinafterForgot =(EditText)getActivity().findViewById(R.id.set_pin_forgot);
        pinafterForgot.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        confirmPinForgot=(EditText)getActivity().findViewById(R.id.confirm_pin_forgot);
        confirmPinForgot.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        submitpinForgot=(Button)getActivity().findViewById(R.id.sub_set_pin_forgot);
        submitpinForgot.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==submitpinForgot){
            changepin();


        }
    }

    private void changepin() {
        String pin = pinafterForgot.getText().toString();
        if (pin.length() ==4)
        {
            if(pin.equals(confirmPinForgot.getText().toString()))
            {

                try {
                    JSONObject requestJson = new JSONObject();
                    JSONObject header = new JSONObject();
                    JSONObject data = new JSONObject();
                    requestJson.put("HEADER", header);
                    data.put("mobileNumber", Preference.getStringPreference(getActivity(),AppConstants.MOBILE_NUMBER));
                    data.put("mNewPin",pin);

                    requestJson.put("DATA", data);

                    VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_CHANGE_FORGOT_PIN), requestJson, changePinResp, true);
                } catch (JSONException e) {
                    Log.e(TAG, "validateReceiveMoney: JSONException", e);
                } catch (InternetNotAvailableException e) {
                    Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                confirmPinForgot.setError(getString(R.string.pins_dont_match));
            }

        }
        else
        {
            pinafterForgot.setError(getString(R.string.enter_valid_pin));

        }
    }
    private VolleyJsonRequest.OnJsonResponse changePinResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
            Preference.savePreference(getActivity(),AppConstants.IS_LOGGED_IN,true);
            loginActivity.changeFragment(new LoginFragment());

        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
        }
    };
}
