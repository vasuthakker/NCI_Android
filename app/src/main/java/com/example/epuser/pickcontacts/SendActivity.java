package com.example.epuser.pickcontacts;

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

import org.json.JSONException;
import org.json.JSONObject;

public class SendActivity extends AppCompatActivity implements View.OnClickListener {
    static final int RESULT_PICK_CONTACT=1;
    private EditText phoneNumber,amount,whatIsItFor;
    private Button sendButton ;
    private QuickContactBadge contactButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

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

        if(v == sendButton)
        {
            if(CheckNetwork.isInternetAvailable(this)) {
                String phone = phoneNumber.getText().toString().replaceAll("\\s+", "");

                if (phone.length() > 9){
                    phone = phone.substring(phone.length() - 10);
                }
                else {
                    phoneNumber.setText(null);
                    amount.setText(null);
                    Toast.makeText(this, "Enter a valid Phone Number", Toast.LENGTH_LONG).show();
                    return;
                }
                String amountToBeSent = amount.getText().toString();

                String url = "http://192.168.10.93:8080/epcore/balance/Loader";

                JSONObject data =new JSONObject();
                try {
                    data.put("HEADER", "FJGH");
                    JSONObject data1 = new JSONObject();
                    data1.put("mobNo",phone);
                    data1.put("reqAmount", 200);
                    data.put("DATA", data1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonRequest = new JsonObjectRequest(url, data, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(SendActivity.this,response.toString(),Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(SendActivity.this,MainActivity.class);
                        startActivity(intent);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SendActivity.this, error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
                );
                RequestQueue requestQueue = Volley.newRequestQueue(SendActivity.this);
                requestQueue.add(jsonRequest);



            }
            else
            {
                Toast.makeText(SendActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
            }

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
                        cursor = SendActivity.this.getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();
                        int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        phoneNo = cursor.getString(phoneIndex);

                        phoneNumber.setText(phoneNo);
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
