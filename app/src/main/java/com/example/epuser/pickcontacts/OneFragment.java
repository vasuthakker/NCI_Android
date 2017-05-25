package com.example.epuser.pickcontacts;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toast;

import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONException;
import org.json.JSONObject;

import static android.app.Activity.RESULT_OK;

/**
 * Created by epuser on 5/18/2017.
 */

public class OneFragment extends Fragment implements View.OnClickListener {



  //  private String TAG = OneFragment.class.getSimpleName();
    static final int RESULT_PICK_CONTACT=1;
    private EditText edtphn;
    private QuickContactBadge btncnt;
    private Button btnshw;
    private TextView textView2;
    private static final String LOG_TAG = "Barcode Scanner API";
    private static final int PHOTO_REQUEST = 10;
    private TextView scanResults;
    private Button button;
    private BarcodeDetector detector;
    private Uri imageUri;
    private static final int REQUEST_WRITE_PERMISSION = 20;
    private static final String SAVED_INSTANCE_URI = "uri";
    private static final String SAVED_INSTANCE_RESULT = "result";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_one,container,false);


        edtphn = (EditText)view.findViewById(R.id.edtphn);
        btncnt=(QuickContactBadge) view.findViewById(R.id.btncnt);
        btnshw =(Button)view.findViewById(R.id.btnshw);
        textView2=(TextView) view.findViewById(R.id.textview2);
        btnshw=(Button)view.findViewById(R.id.button) ;
        scanResults=(TextView)view.findViewById(R.id.scanResults);


        btncnt.setOnClickListener(this);
        btnshw.setOnClickListener(this);


        return view;


    }
    @Override
    public void onClick(View v) {

        if (v == btncnt) {
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
        }
        if (v==btnshw){
            if(CheckNetwork.isInternetAvailable(getActivity())) //returns true if internet available
            {

                //do something. loadwebview.

                try {
//                String phoneNo = null;
//                char phoneDigit ;
//                String Temp = edtphn.getText().toString();
//                for (int i=0;i<Temp.length();i++)
//                {
//                    if(Temp[i] != " ")
//                    {
//
//                    }
//                }
                    String noSpcphn= edtphn.getText().toString().replaceAll("\\s+", "");

                    String lstTen = null;
                   if (noSpcphn.length() >9) {
                        lstTen=noSpcphn.substring(noSpcphn.length()-10);

                   }
                    else{
                       Toast.makeText(getActivity(), "Invalid Phone Number", Toast.LENGTH_SHORT).show();

                   }




                       JSONObject data = new JSONObject();
                    data.put("HEADER", "FJGH");
                    JSONObject data1 = new JSONObject();
                    data1.put("mobNo",lstTen);
                    data1.put("reqAmount", 200);
                    data.put("DATA", data1);

                    // Toast.makeText(this, data.toString(),Toast.LENGTH_SHORT).show();

                    new OneFragment.GetBalance().execute(data.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else
            {
                Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_SHORT).show();
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



    private class GetBalance extends AsyncTask<String, Void, String>
    {
        //        @Override
//        protected void onPreExecute(){
//            super.onPreExecute();
//        }
        @Override
        protected String doInBackground(String... requestBody)
        {
            //String url = "http://192.168.10.93:8080/epscms/loadCard";
            String url = "http://192.168.10.93:8080/epcore/balance/Loader";


            String Result = Utils.makeRequestNGetResponse("POST",url,requestBody[0]);
           // Log.v(TAG, requestBody[0]);
            return Result;

        }
        @Override
        protected void onPostExecute(String result)
        {
           // Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
            try {
                JSONObject Balance = new JSONObject(result);
                textView2.setText(Balance.getString("Finalbalance"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }



}

