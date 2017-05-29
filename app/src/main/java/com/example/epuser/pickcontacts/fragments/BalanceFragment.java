package com.example.epuser.pickcontacts.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toast;

import com.example.epuser.pickcontacts.network.CheckNetwork;
import com.example.epuser.pickcontacts.R;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by epuser on 5/18/2017.
 */

public class BalanceFragment extends android.support.v4.app.Fragment implements View.OnClickListener {


    //  private String TAG = BalanceFragment.class.getSimpleName();
    static final int RESULT_PICK_CONTACT = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
    private EditText edtphn;
    private QuickContactBadge btncnt;
    private Button btnshw;
    private TextView textView2;
    private static final String LOG_TAG = "Barcode Scanner API";
    private static final int PHOTO_REQUEST = 10;
    private TextView scanResults;

    private Button btnScan;
    private BarcodeDetector detector;
    private Uri imageUri;
    private static final int REQUEST_WRITE_PERMISSION = 20;
    private static final String SAVED_INSTANCE_URI = "uri";
    private static final String SAVED_INSTANCE_RESULT = "result";
    private IntentIntegrator qrScan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        init();
    }

    private void init() {

        edtphn = (EditText) getActivity().findViewById(R.id.edtphn);
        btncnt = (QuickContactBadge) getActivity().findViewById(R.id.btncnt);
        btnshw = (Button)getActivity().findViewById(R.id.btnshw);
        textView2 = (TextView) getActivity().findViewById(R.id.textview2);

        btnScan = (Button) getActivity().findViewById(R.id.button);
        scanResults = (TextView) getActivity().findViewById(R.id.scanResults);
        qrScan = new IntentIntegrator(getActivity());


        btncnt.setOnClickListener(this);
        btnshw.setOnClickListener(this);
        btnScan.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btnScan) {
            qrScan.initiateScan();
        }
       else if (v == btncnt) {
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
        }
        else if (v == btnshw) {
            if (CheckNetwork.isInternetAvailable(getActivity())) //returns true if internet available
            {
                try {
                    String noSpcphn = edtphn.getText().toString().replaceAll("\\s+", "");

                    String lstTen = null;
                    if (noSpcphn.length() > 9) {
                        lstTen = noSpcphn.substring(noSpcphn.length() - 10);

                    } else {
                        Toast.makeText(getActivity(), "Invalid Phone Number", Toast.LENGTH_SHORT).show();

                    }
                    JSONObject data = new JSONObject();
                    data.put("HEADER", "FJGH");
                    JSONObject data1 = new JSONObject();
                    data1.put("mobNo", lstTen);
                    data1.put("reqAmount", 200);
                    data.put("DATA", data1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //check whether the result is ok
        if (resultCode == RESULT_OK) {
            // Check for the request cod usign multiple startAe, we might bectivityForReslut
            // switch (requestCode) {
            //  case RESULT_PICK_CONTACT:

            if (requestCode == RESULT_PICK_CONTACT) {
                Cursor cursor = null;
                try {
                    String phoneNo = null;
                    String name = null;
                    Uri uri = data.getData();
                    cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                    cursor.moveToFirst();
                    int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    phoneNo = cursor.getString(phoneIndex);

                    edtphn.setText(phoneNo);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("MainActivity", "Failed to pick contact");

                }

            }
        }





//
//           else if (requestCode == 0) {

//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        if (result != null) {
//            if (result.getContents() == null) {
//                Toast.makeText(getActivity(), "Result Not Found", Toast.LENGTH_LONG).show();
//            } else {
//                    Log.d("QR Result", "onActivityResult:"+result.getContents());
//                Toast.makeText(getActivity(), result.getContents(), Toast.LENGTH_LONG).show();
//                }
//        }
//
      }


}
