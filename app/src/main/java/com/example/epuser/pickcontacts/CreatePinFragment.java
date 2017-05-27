package com.example.epuser.pickcontacts;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.epuser.pickcontacts.common.AppConstants;
import com.example.epuser.pickcontacts.common.Preference;
import com.example.epuser.pickcontacts.common.URLGenerator;
import com.example.epuser.pickcontacts.common.Utils;
import com.example.epuser.pickcontacts.exceptions.InternetNotAvailableException;
import com.example.epuser.pickcontacts.network.VolleyJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by epuser on 5/26/2017.
 */

public class CreatePinFragment extends Fragment {
    private EditText pin_ET , confirm_pin_ET;
    private Button create_pin_btn;
    private LoginPage loginActivity;
    private static final String TAG = "CreatePinFragment";
    private Spinner sequrity_questions_spinner;
    private String selected_security_question = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_create_pin, container, false);

    }
    private void init() {
        pin_ET = (EditText)getActivity().findViewById(R.id.pin_ET);
        confirm_pin_ET = (EditText)getActivity().findViewById(R.id.confirm_pin_ET);
        create_pin_btn = (Button)getActivity().findViewById(R.id.create_pin_btn);
        sequrity_questions_spinner = (Spinner)getActivity().findViewById(R.id.security_question_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.security_questions, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sequrity_questions_spinner.setAdapter(adapter);


    }

    @Override
    public void onStart() {
        super.onStart();
        init();

        create_pin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPin();
            }
        });
        sequrity_questions_spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                selected_security_question =  (parent.getItemAtPosition(position)).toString();
//                Log.v(TAG, "selected question is: " + selected_security_question);
            }
        });

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivity = (LoginPage) context;
    }

    private void createPin() {
        String pin = pin_ET.getText().toString();
        if (pin.length() ==4)
        {
            if(pin.equals(confirm_pin_ET.getText().toString()))
            {
                Preference.savePreference(getActivity(),AppConstants.PIN,pin);
                try {
                    JSONObject requestJson = new JSONObject();
                    JSONObject header = new JSONObject();
                    JSONObject data = new JSONObject();
                    requestJson.put("HEADER", header);
                    data.put("mobileNumber", Preference.getStringPreference(getActivity(),AppConstants.MOBILE_NUMBER));
                    data.put("mPin1",pin);
                    data.put("mPin2",pin);
                    requestJson.put("DATA", data);

                    VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_CREATE_PIN), requestJson, registerResp, true);
                } catch (JSONException e) {
                    Log.e(TAG, "validateReceiveMoney: JSONException", e);
                } catch (InternetNotAvailableException e) {
                    Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                pin_ET.setError("pins did not match");
            }

        }
        else
        {
            confirm_pin_ET.setError("Please enter a four digit pin");

        }
    }
    private VolleyJsonRequest.OnJsonResponse registerResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
            try {
                Preference.savePreference(getActivity(),AppConstants.IS_LOGGED_IN,true);
                String response =jsonObj.getString(AppConstants.KEY_RESP);
                if(response.equals(getString(R.string.request_complete))) {
                    loginActivity.changeFragment(new LoginFragment());
                }

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
