package com.example.epuser.pickcontacts.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;

import com.example.epuser.pickcontacts.R;

public class ReceiveActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ReceiveActivity";
    private QuickContactBadge selectContact;
    private EditText edtreceivePhonenumber,edtenterAmount,receiveOtp,receiveRemarks;
    private Button btnreceiveOtp,receivebtn;
    static final int RESULT_PICK_CONTACT=1;

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
        btnreceiveOtp=(Button)findViewById(R.id.receive_money_btn_otp);
        receiveOtp=(EditText)findViewById(R.id.receive_enter_otp) ;


        btnreceiveOtp.setOnClickListener(this);
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
         else if(v==btnreceiveOtp){

            checkConfirmation();


        }
        else if(v==receivebtn){

            sendOtpToPhonenumber();

            showChangeLangDialog();
//            selectContact.setVisibility(View.GONE);
//            edtreceivePhonenumber.setVisibility(View.GONE);
//            edtenterAmount.setVisibility(View.GONE);
//            receivebtn.setVisibility(View.GONE);
//            selectContact.setVisibility(View.GONE);
//            receiveRemarks.setVisibility(View.GONE);
//
//
//            btnreceiveOtp.setVisibility(View.VISIBLE);
//            receiveOtp.setVisibility(View.VISIBLE);



        }
    }

    private void sendOtpToPhonenumber() {
    }

    private void checkConfirmation() {


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

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

        dialogBuilder.setTitle("Custom dialog");
        dialogBuilder.setMessage("Enter text below");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
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
}


