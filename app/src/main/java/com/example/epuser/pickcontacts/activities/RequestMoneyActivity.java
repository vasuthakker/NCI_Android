package com.example.epuser.pickcontacts.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.example.epuser.pickcontacts.R;

public class RequestMoneyActivity extends AppCompatActivity  implements View.OnClickListener{
    private QuickContactBadge requestcnt;
    static final int RESULT_PICK_CONTACT=1;
    private EditText requestphn,requestAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        requestcnt=(QuickContactBadge)findViewById(R.id.reqmoney_contact);
        requestphn=(EditText)findViewById(R.id.reqmoney_edtmobile);
        requestAmount=(EditText)findViewById(R.id.reqmoney_edtamount);
        requestcnt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==requestcnt)
        {
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);

        }
    }

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
                        requestphn.setText(phoneNo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }




}
