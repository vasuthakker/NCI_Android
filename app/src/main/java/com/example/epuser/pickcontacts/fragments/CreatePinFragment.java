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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.activities.LoginPage;
import com.example.epuser.pickcontacts.common.AppConstants;
import com.example.epuser.pickcontacts.common.Preference;
import com.example.epuser.pickcontacts.common.URLGenerator;
import com.example.epuser.pickcontacts.common.Utils;
import com.example.epuser.pickcontacts.entities.QuestionDetails;
import com.example.epuser.pickcontacts.exceptions.InternetNotAvailableException;
import com.example.epuser.pickcontacts.network.VolleyJsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.message;

/**
 * Created by epuser on 5/26/2017.
 */

public class CreatePinFragment extends Fragment {
    private EditText pin_ET , confirm_pin_ET,secAnsET;
    private Button create_pin_btn;
    private LoginPage loginActivity;
    private static final String TAG = "CreatePinFragment";
    private Spinner spQuestions;
    private int selected_qn_position =0;
    private int selected_sec_Id ;
    private List<QuestionDetails> questions;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_create_pin, container, false);

    }
    private void init() {
        pin_ET = (EditText)getActivity().findViewById(R.id.pin_ET);
        confirm_pin_ET = (EditText)getActivity().findViewById(R.id.confirm_pin_ET);
        create_pin_btn = (Button)getActivity().findViewById(R.id.create_pin_btn);
        spQuestions = (Spinner)getActivity().findViewById(R.id.security_question_spinner);
        secAnsET = (EditText)getActivity().findViewById(R.id.security_answer_ET);

    }


    @Override
    public void onStart() {
        super.onStart();
        init();
        getSecurityQuestions();

        create_pin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // saveSecurityAns();
                createPinAndSecQn();
            }
        });
        spQuestions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                QuestionDetails question =  questions.get(position);
                selected_sec_Id = question.getId();
                selected_qn_position = position;
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

    private void createPinAndSecQn() {
        if(selected_qn_position ==0)
        {
            Toast.makeText(getActivity(),"Please select a question",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(secAnsET.getText().toString()))
        {
            secAnsET.setError(getString(R.string.enter_sec_ans));
            return;
        }
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
                    requestJson.put(getString(R.string.header), header);
                    data.put(getString(R.string.mobile_number), Preference.getStringPreference(getActivity(),AppConstants.MOBILE_NUMBER));
                    data.put("mPin1",pin);
                    data.put("mPin2",pin);
                    data.put(getString(R.string.sec_qn_id),selected_sec_Id);
                    data.put(getString(R.string.sec_ans_key),secAnsET.getText().toString());
                    requestJson.put("DATA", data);

                    VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_CREATE_PIN_AND_SEC_QN), requestJson, createPinAndSecQnResp, true);
                } catch (JSONException e) {
                    Log.e(TAG, "validateReceiveMoney: JSONException", e);
                } catch (InternetNotAvailableException e) {
                    Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                confirm_pin_ET.setError(getString(R.string.pins_dont_match));
            }

        }
        else
        {
            pin_ET.setError(getString(R.string.enter_valid_pin));

        }
    }
    private VolleyJsonRequest.OnJsonResponse createPinAndSecQnResp = new VolleyJsonRequest.OnJsonResponse() {
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



    private void getSecurityQuestions() {
        try {
            JSONObject requestJson = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            requestJson.put("HEADER", jsonObject1);

            VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_GET_SEC_QNS), requestJson, securityQuestionsResp, true);
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
                questions=new ArrayList<>();
                JSONArray queArray=jsonObj.getJSONArray("DATA");
                QuestionDetails question;
                question = new QuestionDetails();
                question.setId(0);
                question.setValue(getString(R.string.select_sec_qn));
                questions.add(question);
                for (int i = 0; i <queArray.length() ; i++) {
                    JSONObject objQue=queArray.getJSONObject(i);
                    question=new QuestionDetails();
                    question.setId(objQue.getInt("Id"));
                    question.setValue(objQue.getString("SecQue"));
                    questions.add(question);



                }
                ArrayAdapter<QuestionDetails> adapter = new ArrayAdapter<QuestionDetails>(getActivity(),android.R.layout.simple_list_item_1,questions);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spQuestions.setAdapter(adapter);

            } catch (JSONException e) {
                Log.e(TAG,"",e);
            }


        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
        }
    };
}
