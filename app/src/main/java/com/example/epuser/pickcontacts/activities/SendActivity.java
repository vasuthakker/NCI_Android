package com.example.epuser.pickcontacts.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.epuser.pickcontacts.network.CheckNetwork;

import org.json.JSONException;
import org.json.JSONObject;

public class SendActivity extends AppCompatActivity implements View.OnClickListener {
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
                        sendphoneNumber.setText(phoneNo);
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
