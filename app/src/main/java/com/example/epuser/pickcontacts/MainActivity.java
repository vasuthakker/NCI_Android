package com.example.epuser.pickcontacts;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
//import android.content.Intent;
//import android.database.Cursor;
//import android.net.Uri;
//import android.nfc.Tag;
//import android.os.AsyncTask;
//import android.provider.ContactsContract;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
import android.widget.ImageView;
//import android.widget.Toast;

//import org.json.JSONException;
//import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
 //   private String TAG = MainActivity.class.getSimpleName();
   // static final int RESULT_PICK_CONTACT = 1;
   // private EditText edtphn;
   // private Button btncnt;
   // private Button btnshw;
   // private TextView textView2;
    private ImageView txtchk,imagerequest;
    private ImageView txtsnd;
    private ImageView txtad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // edtphn = (EditText) findViewById(R.id.edtphn);
       // btncnt = (Button) findViewById(R.id.btncnt);
       // btnshw = (Button) findViewById(R.id.btnshw);
       // textView2 = (TextView) findViewById(R.id.textview2);
        txtchk = (ImageView) findViewById(R.id.main_imgbalance);
        txtsnd = (ImageView) findViewById(R.id.main_imgpay);
        txtad = (ImageView) findViewById(R.id.main_imgadd);
        imagerequest= (ImageView) findViewById(R.id.main_imgreqmoney);

       // btncnt.setOnClickListener(this);
       // btnshw.setOnClickListener(this);
        txtchk.setOnClickListener(this);
        txtsnd.setOnClickListener(this);
        txtad.setOnClickListener(this);
        imagerequest.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        /*if (v==btncnt ){
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
        }*/
        if (v == txtad) {
            FragmentManager manager = getFragmentManager();
            ThreeFragment threeFragment = new ThreeFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fcontainer, threeFragment, "threeFragment");
            transaction.commit();
        }
        if (v == txtchk) {
            FragmentManager manager = getFragmentManager();
            BalanceFragment balanceFragment = new BalanceFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fcontainer, balanceFragment, "balanceFragment");
            transaction.commit();

        }
        if (v == txtsnd) {
            FragmentManager manager = getFragmentManager();
            TwoFragment twoFragment = new TwoFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fcontainer, twoFragment, "twoFragment");
            transaction.commit();
        }
        if(v==imagerequest){
            Intent intent= new Intent(MainActivity.this,Request.class);
            startActivity(intent);
        }
       /* if (v==btnshw){
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

                JSONObject data = new JSONObject();
                data.put("HEADER", "FJGH");
                JSONObject data1 = new JSONObject();
                data1.put("mobNo", edtphn.getText().toString().replaceAll("\\s+",""));
                data1.put("reqAmount", 200);
                data.put("DATA", data1);

                Toast.makeText(this,data.toString(),Toast.LENGTH_SHORT).show();

                new GetBalance().execute(data.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }



   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                        cursor = getContentResolver().query(uri, null, null, null, null);
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
            Log.v(TAG, requestBody[0]);
            return Result;

        }
        @Override
        protected void onPostExecute(String result)
        {
            Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
            try {
                JSONObject Balance = new JSONObject(result);
               textView2.setText(Balance.getString("Finalbalance"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }*/


    }
}