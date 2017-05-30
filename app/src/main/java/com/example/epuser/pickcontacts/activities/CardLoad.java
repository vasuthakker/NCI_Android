package com.example.epuser.pickcontacts.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
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

public class CardLoad extends AppCompatActivity implements View.OnClickListener {
    private EditText cl_phonenumber,cl_amount;
    private QuickContactBadge cl_contact;
    private Button cl_btn;
    static final int RESULT_PICK_CONTACT=1;
    private static final String TAG = "CardLoad";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_load);
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        cl_amount=(EditText)findViewById(R.id.cl_amount);
        cl_btn=(Button)findViewById(R.id.cl_btn);
        cl_contact=(QuickContactBadge)findViewById(R.id.cl_quickContactBadge);
        cl_phonenumber=(EditText)findViewById(R.id.cl_phnonenumber);

        cl_contact.setOnClickListener(this);
        cl_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==cl_contact){
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);

        }

        else if(v==cl_btn){

            cardLoadServer();

        }
    }

    private void cardLoadServer() {
        String mobile = cl_phonenumber.getText().toString();
        String amountreceive=cl_amount.getText().toString();
      //  String remarksreceive=receiveRemarks.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            cl_phonenumber.setError(getString(R.string.enter_mobile));
            return;
        } else if (mobile.length() < 10) {
            cl_phonenumber.setError(getString(R.string.enter_valid_mobile));
            return;
        } else if (mobile.length() > 9)
            mobile = mobile.substring(mobile.length() - 10);



        try {
            JSONObject requestJson = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            requestJson.put("HEADER", jsonObject1);
            jsonObject2.put("mobilenumberCardLoad", mobile);
            jsonObject2.put("amount",amountreceive);
           // jsonObject2.put("remarks",remarksreceive);
            jsonObject2.put("mobileNumber", Preference.getStringPreference(this, AppConstants.MOBILE_NUMBER));
            requestJson.put("DATA", jsonObject2);

            VolleyJsonRequest.request(this, Utils.generateURL(URLGenerator.URL_CARD_LOAD), requestJson, CardLoadResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateCardLoad: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(this, getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private VolleyJsonRequest.OnJsonResponse CardLoadResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {


        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(CardLoad.this, message);
        }
    };



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check whether the result is ok
        if (resultCode == RESULT_OK) {
            // Check for the request cod usign multiple startAe, we might bectivityForReslut
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    Cursor cursor = null;
                    try {
                        String phoneNo = null;
                        String name = null;
                        Uri uri = data.getData();
                        cursor = getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();
                        int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        phoneNo = cursor.getString(phoneIndex);
                        cl_phonenumber.setText(phoneNo);
                    } catch (Exception e) {
                        Log.e(TAG, "unable to load contact", e);
                    }
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }
}
