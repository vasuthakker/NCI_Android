package com.example.epuser.pickcontacts.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
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

public class ReceiveActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ReceiveActivity";
    private QuickContactBadge selectContact;
    private EditText edtreceivePhonenumber,edtenterAmount,receiveRemarks;
    private Button btnreceiveOtp,receivebtn;
    static final int RESULT_PICK_CONTACT=1;
    private static final String TAG = "ReceiveActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        selectContact =(QuickContactBadge)findViewById(R.id.receivemoney_contact);
        edtreceivePhonenumber=(EditText)findViewById(R.id.receivemoney_edtmobile) ;
        edtenterAmount=(EditText) findViewById(R.id.receivemoney_edtamount);
        receiveRemarks=(EditText)findViewById(R.id.receivemoney_edtremakrs) ;

        receivebtn=(Button)findViewById(R.id.receivebutton) ;




        selectContact.setOnClickListener(this);
        receivebtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==selectContact){
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
        }

        else if(v==receivebtn){

            sendOtpToBenefactor();




        }
    }



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

                        edtreceivePhonenumber.setText(phoneNo);
                    } catch (Exception e) {
                        Log.e(TAG,"",e);
                    }
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }

    public void showOTPDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

        dialogBuilder.setTitle("OTP");
        dialogBuilder.setMessage("Enter the OTP sent to Benefactor's Phone Number ");
        dialogBuilder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

              checkConfirmation();
                //do something with edt.getText().toString();
                //send to server fpr verification
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {


                //pass

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void sendOtpToBenefactor() {

        String mobile = edtreceivePhonenumber.getText().toString();
        String amountreceive=edtenterAmount.getText().toString();
        String remarksreceive=receiveRemarks.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            edtreceivePhonenumber.setError(getString(R.string.enter_mobile));
            return;
        } else if (mobile.length() < 10) {
            edtreceivePhonenumber.setError(getString(R.string.enter_valid_mobile));
            return;
        } else if (mobile.length() > 9)
            mobile = mobile.substring(mobile.length() - 10);



        try {
            JSONObject requestJson = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            requestJson.put("HEADER", jsonObject1);
            jsonObject2.put("mobilenumberofbenefactor", mobile);
            jsonObject2.put("amount",amountreceive);
            jsonObject2.put("remarks",remarksreceive);
            jsonObject2.put("mobileNumber", Preference.getStringPreference(this, AppConstants.MOBILE_NUMBER));
            requestJson.put("DATA", jsonObject2);

            VolleyJsonRequest.request(this, Utils.generateURL(URLGenerator.URL_OTP_BENEFACTOR), requestJson, otpBenefactorResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(this, getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private VolleyJsonRequest.OnJsonResponse otpBenefactorResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
            showOTPDialog();

        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(ReceiveActivity.this, message);
        }
    };



    private void checkConfirmation() {




    }
}


