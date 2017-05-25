package com.example.epuser.pickcontacts;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epuser.pickcontacts.common.URLGenerator;
import com.example.epuser.pickcontacts.common.Utils;
import com.example.epuser.pickcontacts.exceptions.InternetNotAvailableException;
import com.example.epuser.pickcontacts.network.VolleyJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by epuser on 5/22/2017.
 */

public class SendOTP extends Fragment implements View.OnClickListener {
    private static final String TAG = "SendOTP";
    private EditText typeotp;
    private Button subotp;
    private TextView textotp;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.otp_send,container,false);
        typeotp =(EditText)view.findViewById(R.id.typeotp);
        subotp=(Button) view.findViewById(R.id.subotp);
        textotp=(TextView)view.findViewById(R.id.textotp);
        subotp.setOnClickListener(this);
        return  view;
    }

    @Override
    public void onClick(View v) {
        if(v==subotp){
           // submitOTP();
        }

    }

    private void submitOTP()
    {
        String mobile = textotp.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            textotp.setError(getString(R.string.enter_mobile));
            return;
        } else if (mobile.length() < 10) {
            textotp.setError(getString(R.string.enter_valid_mobile));
            return;
        } else if (mobile.length() > 10)
            mobile = mobile.substring(mobile.length() - 10);

        try {
            JSONObject requestJson = new JSONObject();
            requestJson.put("mobile", mobile);
            VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_LOGIN), requestJson, loginResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private VolleyJsonRequest.OnJsonResponse loginResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {


        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
        }
    };

    private class ReceiveAsyncTask extends AsyncTask<String,Void ,String>
    {

        @Override
        protected String doInBackground(String... params) {

            String result = "";
            return result;
        }
        @Override
        protected void onPostExecute(String result)
        {

            Toast.makeText(getActivity(),result,Toast.LENGTH_LONG).show();
            String value = getArguments().getString("YourKey");
            if (value =="hey"){
                Intent intent=new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
            else if (value =="LoginPage") {
                startActivity(new Intent(getActivity(), LoginPage.class));
                getActivity().finish();
            }


              // here we have to decide which activity to open
            //if success update sharedpreferences, fetch data , go to homepage
            //else try again logging in

        }
        }
    }

