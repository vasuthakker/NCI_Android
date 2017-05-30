package com.example.epuser.pickcontacts.activities;


//import android.content.Intent;
//import android.database.Cursor;
//import android.net.Uri;
//import android.nfc.Tag;
//import android.os.AsyncTask;
//import android.provider.ContactsContract;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
import android.widget.ImageView;

import com.example.epuser.pickcontacts.R;
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
    private ImageView checkBalance,imagerequest,sendBalance,addMoney,receiveMoney;
    private Menu menusetting,changepin,profile,trans_history;
    private Toolbar myToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        // edtphn = (EditText) findViewById(R.id.edtphn);
        // btncnt = (Button) findViewById(R.id.btncnt);
        // btnshw = (Button) findViewById(R.id.btnshw);
        // textView2 = (TextView) findViewById(R.id.textview2);
        checkBalance = (ImageView) findViewById(R.id.main_imgbalance);
        sendBalance = (ImageView) findViewById(R.id.main_imgpay);
        addMoney = (ImageView) findViewById(R.id.main_imgadd);
        imagerequest= (ImageView) findViewById(R.id.main_imgreqmoney);
        receiveMoney=(ImageView)findViewById(R.id.main_imgaccept) ;
        menusetting=(Menu)findViewById(R.id.menusetting);
        changepin=(Menu)findViewById(R.id.changepin);
        profile=(Menu)findViewById(R.id.profile);
        myToolbar = (Toolbar) findViewById(R.id.toolbar5);
        trans_history=(Menu)findViewById(R.id.trans_history);
        setSupportActionBar(myToolbar);

        // btncnt.setOnClickListener(this);
        // btnshw.setOnClickListener(this);
        checkBalance.setOnClickListener(this);
        sendBalance.setOnClickListener(this);
        addMoney.setOnClickListener(this);
        receiveMoney.setOnClickListener(this);
        imagerequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        /*if (v==btncnt ){
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
        }*/
        if (v == addMoney) {
            Intent intent = new Intent(MainActivity.this, AddMoneyActivity.class);
            startActivity(intent);

        } else if (v == checkBalance) {


            Intent intent = new Intent(MainActivity.this, BalanceActivity.class);
            startActivity(intent);

        } else if (v == sendBalance) {
            Intent intent = new Intent(MainActivity.this, SendActivity.class);
            startActivity(intent);
        } else if (v == imagerequest) {
            Intent intent = new Intent(MainActivity.this, RequestMoneyActivity.class);
            startActivity(intent);
        } else if (v == receiveMoney) {
            Intent intent = new Intent(MainActivity.this, ReceiveActivity.class);
            startActivity(intent);
        }
    }



        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.changepin) {
                //Do something
                Intent intent=new Intent(MainActivity.this,ChangePin.class);
                startActivity(intent);
                return true;
            } else if (id == R.id.profile) {
                //Do something
                return true;
            } else if (id == R.id.menusetting) {

//                addNotification();
                return true;
            }else if(id==R.id.trans_history){

                return true;
            }

            return super.onOptionsItemSelected(item);
        }

//    private void addNotification() {
//
//        NotificationCompat.Builder builder =
//                (NotificationCompat.Builder) new  NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.progress_2)
//                        .setContentTitle("Notifications Example")
//                        .setContentText("This is a test notification");
//
//        Intent notificationIntent = new Intent(this, LoginPage.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(contentIntent);
//
//        // Add as notification
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(0, builder.build());
//    }
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
                Log.e(TAG,"",e);
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
                        Log.e(TAG,"",e);
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
                Log.e(TAG,"",e);
            }


        }

    }*/



