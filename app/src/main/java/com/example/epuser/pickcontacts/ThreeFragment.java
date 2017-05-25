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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.example.epuser.pickcontacts.common.URLGenerator;
import com.example.epuser.pickcontacts.common.Utils;
import com.example.epuser.pickcontacts.exceptions.InternetNotAvailableException;
import com.example.epuser.pickcontacts.network.VolleyJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import static android.app.Activity.RESULT_OK;


public class ThreeFragment extends Fragment  implements View.OnClickListener{
    private static final String TAG = "ThreeFragment";
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

        if (v==contactBadge)
        {
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
        }

        if (v == receiveButton)
        {
            receive();
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

    private void receive() {
        String mobile = TFphoneNumber.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            TFphoneNumber.setError(getString(R.string.enter_mobile));
            return;
        } else if (mobile.length() < 10) {
            TFphoneNumber.setError(getString(R.string.enter_valid_mobile));
            return;
        } else if (mobile.length() > 10)
            mobile = mobile.substring(mobile.length() - 10);

        try {
            JSONObject requestJson = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            requestJson.put("HEADER",jsonObject1);
            jsonObject2.put("mobileNumber" , "7738723068");
            requestJson.put("DATA",jsonObject2);

            Toast.makeText(getActivity(),requestJson.toString(),Toast.LENGTH_LONG).show();
           // requestJson.put("mobile", mobile);
           // VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_OTP), requestJson, loginResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        }
//        } catch (InternetNotAvailableException e) {
//            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
//        }
    }

    private VolleyJsonRequest.OnJsonResponse loginResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
            FragmentManager manager = getFragmentManager();
            SendOTP sendOTP = new SendOTP();
            Bundle args = new Bundle();
            args.putString("YourKey", diff);
            sendOTP.setArguments(args);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fcontainer, sendOTP, "sendOTP");

            transaction.commit();

        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
        }
    };
}


