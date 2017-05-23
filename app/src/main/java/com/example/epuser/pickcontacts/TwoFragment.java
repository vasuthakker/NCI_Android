package com.example.epuser.pickcontacts;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import static android.app.Activity.RESULT_OK;

/**
 * Created by epuser on 5/18/2017.
 */

public class TwoFragment extends Fragment  implements View.OnClickListener{
    static final int RESULT_PICK_CONTACT=1;
    private EditText edtphn,inputamount;
    private QuickContactBadge btncnt;
    private Button sendButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);

        edtphn = (EditText) view.findViewById(R.id.edtphn);
        inputamount=(EditText)view.findViewById(R.id.inputamount);
        btncnt = (QuickContactBadge) view.findViewById(R.id.btncnt);
        sendButton=(Button)view.findViewById(R.id.btnsnd);


        sendButton.setOnClickListener(this);
        btncnt.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == btncnt) {
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
        }

        if (v == sendButton)
        {

            if(CheckNetwork.isInternetAvailable(getActivity())) {
                String phone = edtphn.getText().toString().replaceAll("\\s+", "");

                if (phone.length() > 9){
                    phone = phone.substring(phone.length() - 10);
                }
                else {
                    edtphn.setText(null);
                    inputamount.setText(null);
                    Toast.makeText(getActivity(), "Enter a valid Phone Number", Toast.LENGTH_LONG).show();
                    return;
                }
                String amount = inputamount.getText().toString();

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

                new TwoFragment.SendAsyncTask().execute("POST", url, data.toString());
            }
            else
            {
                Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_LONG).show();
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
                        cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();
                        int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        phoneNo = cursor.getString(phoneIndex);

                        edtphn.setText(phoneNo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }

    private class SendAsyncTask extends AsyncTask<String,Void ,String>
    {

        @Override
        protected String doInBackground(String... params) {

            String result = Utils.makeRequestNGetResponse(params[0],params[1],params[2]);
            return result;
        }
        @Override
        protected void onPostExecute(String result)
        {

            Toast.makeText(getActivity(),result,Toast.LENGTH_LONG).show();

            //if success update sharedpreferences, fetch data , go to homepage
            //else try again logging in

        }
    }

}



