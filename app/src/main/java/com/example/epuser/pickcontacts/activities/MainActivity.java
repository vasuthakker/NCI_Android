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
import android.widget.Toast;

import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.common.AppConstants;
import com.example.epuser.pickcontacts.common.Preference;
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
    private ImageView checkBalance, imagerequest, sendBalance, addMoney, receiveMoney, cardLoad;
    private Menu menusetting, changepin, profile, trans_history;
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
        imagerequest = (ImageView) findViewById(R.id.main_imgreqmoney);
        receiveMoney = (ImageView) findViewById(R.id.main_imgaccept);
        cardLoad = (ImageView) findViewById(R.id.card_load);
        menusetting = (Menu) findViewById(R.id.menusetting);
        changepin = (Menu) findViewById(R.id.changepin);
        profile = (Menu) findViewById(R.id.profile);
        myToolbar = (Toolbar) findViewById(R.id.toolbar5);
        trans_history = (Menu) findViewById(R.id.trans_history);
        setSupportActionBar(myToolbar);

        // btncnt.setOnClickListener(this);
        // btnshw.setOnClickListener(this);
        cardLoad.setOnClickListener(this);
        checkBalance.setOnClickListener(this);
        sendBalance.setOnClickListener(this);
        addMoney.setOnClickListener(this);
        receiveMoney.setOnClickListener(this);
        imagerequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

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
        } else if (v == cardLoad) {
            Intent intent = new Intent(MainActivity.this, CardLoad.class);
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
            Intent intent = new Intent(MainActivity.this, ChangePin.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.profile) {
            Toast.makeText(this, "Your Contact Number is: " + Preference.getStringPreference(this, AppConstants.MOBILE_NUMBER), Toast.LENGTH_LONG).show();


            return true;
        } else if (id == R.id.menusetting) {

//                addNotification();
            return true;
        } else if (id == R.id.trans_history) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
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

