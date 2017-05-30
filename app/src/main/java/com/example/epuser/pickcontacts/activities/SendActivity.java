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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.common.AppConstants;
import com.example.epuser.pickcontacts.common.Preference;
import com.example.epuser.pickcontacts.common.URLGenerator;
import com.example.epuser.pickcontacts.common.Utils;
import com.example.epuser.pickcontacts.exceptions.InternetNotAvailableException;
import com.example.epuser.pickcontacts.fragments.SendOTP;
import com.example.epuser.pickcontacts.network.CheckNetwork;
import com.example.epuser.pickcontacts.network.VolleyJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class SendActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SendActivity";
    static final int RESULT_PICK_CONTACT=1;
    private EditText sendphoneNumber,sendamount,whatIsItFor;
    private Button sendButton ;
    private QuickContactBadge contactButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);


    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    private void init() {
       sendphoneNumber =(EditText)findViewById(R.id.sendmoney_edtmobile);
        sendamount=(EditText)findViewById(R.id.sendmoney_edtamount);
        whatIsItFor = (EditText)findViewById(R.id.sendmoney_edtremarks);
        sendButton=(Button)findViewById(R.id.sendButton);
        contactButton=(QuickContactBadge)findViewById(R.id.sendmoney_contact);

        sendButton.setOnClickListener(this);
        contactButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v ==contactButton)
        {
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
        }

        else if(v == sendButton) {
            send();
        }

    }

    private void send() {
        String sendMobile = sendphoneNumber.getText().toString();
        if (TextUtils.isEmpty(sendMobile)) {
            sendphoneNumber.setError(getString(R.string.enter_mobile));
            return;
        } else if (sendMobile.length() < 10) {
            sendphoneNumber.setError(getString(R.string.enter_valid_mobile));
            return;
        } else if (sendMobile.length() > 9)
            sendMobile = sendMobile.substring(sendMobile.length() - 10);
        String remarks = whatIsItFor.getText().toString();
        String amountToBeSend = sendamount.getText().toString();
        if(TextUtils.isEmpty(amountToBeSend))
        {
            sendamount.setError(getString(R.string.please_enter_amount));
            return;
        }

        try {
            JSONObject requestJson = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            requestJson.put(getString(R.string.header), jsonObject1);
            jsonObject2.put(getString(R.string.mobile_number), sendMobile);
            requestJson.put(getString(R.string.data), jsonObject2);

            VolleyJsonRequest.request(SendActivity.this, Utils.generateURL(URLGenerator.URL_OTP), requestJson, sendResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(SendActivity.this, getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private VolleyJsonRequest.OnJsonResponse sendResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
            try {
                String response =jsonObj.getString(AppConstants.KEY_RESP);
                if(response.equals(getString(R.string.request_complete))) {
                    Toast.makeText(SendActivity.this,"money successfully sent",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SendActivity.this,MainActivity.class));


                }

            } catch (JSONException e) {
                Log.e(TAG,"",e);
            }

        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(SendActivity.this, message);
        }
    };



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check whether the result is ok
        if (resultCode == RESULT_OK) {
            // Check for the request cod usign multiple startAe, we might bectivityForReslut
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    Cursor cursor = null;
                    try {
                        String phoneNo = null ;
                        String name = null;
                        Uri uri = data.getData();
                        cursor =getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();
                        int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        phoneNo = cursor.getString(phoneIndex);
                        sendphoneNumber.setText(phoneNo);
                    } catch (Exception e) {
                        Log.e(TAG,"",e);
                    }
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }
}
