package com.example.epuser.pickcontacts.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.epuser.pickcontacts.activities.LoginPage;
import com.example.epuser.pickcontacts.R;
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
    private EditText pin_ET , confirm_pin_ET ;
    private Button create_pin_btn;
    private LoginPage loginActivity;
    private static final String TAG = "CreatePinFragment";
    private Spinner security_questions_spinner;
    private String selected_security_question = null;
    private TextView textview6;
    private EditText edtsecurityanswer;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_create_pin, container, false);

    }
    private void init() {
        pin_ET = (EditText)getActivity().findViewById(R.id.pin_ET);
        confirm_pin_ET = (EditText)getActivity().findViewById(R.id.confirm_pin_ET);
        create_pin_btn = (Button)getActivity().findViewById(R.id.create_pin_btn);
        security_questions_spinner = (Spinner)getActivity().findViewById(R.id.security_question_spinner);
        textview6=(TextView)getActivity().findViewById(R.id.textView6);
        edtsecurityanswer=(EditText)getActivity().findViewById(R.id.security_answer_ET);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.security_questions, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        security_questions_spinner.setAdapter(adapter);


    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        getSecurityQuestions();

        create_pin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPin();
            }
        });
        security_questions_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(position!=0)
               {
                   selected_security_question = parent.getItemAtPosition(position).toString();
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

                    VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_CREATE_PIN), requestJson, createPinResp, true);
                } catch (JSONException e) {
                    Log.e(TAG, "validateReceiveMoney: JSONException", e);
                } catch (InternetNotAvailableException e) {
                    Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                pin_ET.setError(getString(R.string.pins_dont_match));
            }

        }
        else
        {
            confirm_pin_ET.setError(getString(R.string.enter_valid_pin));

        }
    }
    private VolleyJsonRequest.OnJsonResponse createPinResp = new VolleyJsonRequest.OnJsonResponse() {
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

    private void getSecurityQuestions() {
        try {
            JSONObject requestJson = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            requestJson.put("HEADER", jsonObject1);
            jsonObject2.put("mobileNumber", "");
            requestJson.put("DATA", jsonObject2);

            VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_OTP), requestJson, securityQuestionsResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private VolleyJsonRequest.OnJsonResponse securityQuestionsResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
            try {
                String response =jsonObj.getString(AppConstants.KEY_RESP);
                if(response.equals(getString(R.string.request_complete))) {


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
