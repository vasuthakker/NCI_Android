package com.example.epuser.pickcontacts;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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


public class ThreeFragment extends Fragment  implements View.OnClickListener{
    static final int RESULT_PICK_CONTACT=1;
    private EditText TFphoneNumber,TFamount;
    private Button receiveButton;
    private QuickContactBadge contactBadge;
    private String diff="hey";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_three,container,false);
        TFphoneNumber= (EditText)view.findViewById(R.id.edtPhonenumber);
        TFamount=(EditText)view.findViewById(R.id.edtamount);
        receiveButton=(Button)view.findViewById(R.id.btnreceive);
        contactBadge=(QuickContactBadge)view.findViewById(R.id.buttonContact);
        contactBadge.setOnClickListener(this);
        receiveButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        if (v==contactBadge){
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
        }

        if (v == receiveButton)
        {
            if(CheckNetwork.isInternetAvailable(getActivity())) {
                String phone = TFphoneNumber.getText().toString().replaceAll("\\s+", "");

                if (phone.length() > 9){
                    phone = phone.substring(phone.length() - 10);
                }
                else {
                    TFphoneNumber.setText(null);
                    TFamount.setText(null);
                    Toast.makeText(getActivity(), "Enter a valid Phone Number", Toast.LENGTH_LONG).show();
                    return;
                }
                String amount = TFamount.getText().toString();
               // String url = "http://192.168.10.93:8080/epcore/balance/Loader";
                String url = "http://api.androidhive.info/contacts/";
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
                //create Jsonobject to be sent to backend
                new ThreeFragment.ReceiveAsyncTask().execute("POST", url, data.toString());
            }
            else
            {
                Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_LONG).show();
            }

        }

    }

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


            FragmentManager manager = getFragmentManager();
            SendOTP sendOTP = new SendOTP();
            Bundle args = new Bundle();
            args.putString("YourKey", diff);
            sendOTP.setArguments(args);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fcontainer, sendOTP, "sendOTP");

            transaction.commit();

            //if success update sharedpreferences, fetch data , go to homepage
            //else try again logging in

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

                        TFphoneNumber.setText(phoneNo);
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


