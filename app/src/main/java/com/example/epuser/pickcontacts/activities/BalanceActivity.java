package com.example.epuser.pickcontacts.activities;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.common.AppConstants;
import com.example.epuser.pickcontacts.common.Preference;
import com.example.epuser.pickcontacts.common.URLGenerator;
import com.example.epuser.pickcontacts.common.Utils;
import com.example.epuser.pickcontacts.exceptions.InternetNotAvailableException;
import com.example.epuser.pickcontacts.network.VolleyJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class BalanceActivity extends AppCompatActivity {
    private TextView currentBalance;
    private static final String TAG = "BalanceActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        currentBalance=(TextView)findViewById(R.id.currentbalance);
        showBalance();
    }

    private void showBalance() {
        try {
            JSONObject requestJson = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            requestJson.put(getString(R.string.header), jsonObject1);
            jsonObject2.put(getString(R.string.mobile_number), Preference.getStringPreference(BalanceActivity.this,AppConstants.MOBILE_NUMBER));
            requestJson.put(getString(R.string.data), jsonObject2);

            VolleyJsonRequest.request(BalanceActivity.this, Utils.generateURL(URLGenerator.URL_FETCH_BAlANCE), requestJson, sendResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(BalanceActivity.this, getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }

    }

    private VolleyJsonRequest.OnJsonResponse sendResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
            try {
                String response =jsonObj.getString(AppConstants.KEY_RESP);
                if(response.equals(getString(R.string.request_complete))) {

                }

            } catch (JSONException e) {
                Log.e(TAG,"",e);
            }

        }
        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(BalanceActivity.this, message);
        }
    };

}
