package com.example.epuser.pickcontacts.fragments;


//import android.content.Intent;
//import android.database.Cursor;
//import android.net.Uri;
//import android.nfc.Tag;
//import android.os.AsyncTask;
//import android.provider.ContactsContract;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
//import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.activities.AddMoneyActivity;
import com.example.epuser.pickcontacts.activities.BalanceActivity;
import com.example.epuser.pickcontacts.activities.CardLoad;
import com.example.epuser.pickcontacts.activities.ReceiveActivity;
import com.example.epuser.pickcontacts.activities.RequestMoneyActivity;
import com.example.epuser.pickcontacts.activities.SendActivity;
//import android.widget.Toast;

//import org.json.JSONException;
//import org.json.JSONObject;

public class MainFragment extends Fragment implements View.OnClickListener {
    //   private String TAG = MainFragment.class.getSimpleName();
    // static final int RESULT_PICK_CONTACT = 1;
    // private EditText edtphn;
    // private Button btncnt;
    // private Button btnshw;
    // private TextView textView2;
    private ImageView checkBalance, imagerequest, sendBalance, addMoney, receiveMoney, cardLoad;
    private Menu menusetting, changepin, profile, trans_history;
    private Toolbar myToolbar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }



    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {

        checkBalance = (ImageView)getActivity().findViewById(R.id.main_imgbalance);
        sendBalance = (ImageView)getActivity().findViewById(R.id.main_imgpay);
        addMoney = (ImageView)getActivity().findViewById(R.id.main_imgadd);
        imagerequest = (ImageView)getActivity().findViewById(R.id.main_imgreqmoney);
        receiveMoney = (ImageView)getActivity().findViewById(R.id.main_imgaccept);
        cardLoad = (ImageView)getActivity().findViewById(R.id.card_load);
//        menusetting = (Menu) findViewById(R.id.menusetting);
//        changepin = (Menu) findViewById(R.id.changepin);
      //  profile = (Menu) findViewById(R.id.profile);
//        myToolbar = (Toolbar) findViewById(R.id.toolbar5);
//        trans_history = (Menu) findViewById(R.id.trans_history);
//        setSupportActionBar(myToolbar);


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
            Intent intent = new Intent(getActivity(), AddMoneyActivity.class);
            startActivity(intent);

        } else if (v == checkBalance) {
            Intent intent = new Intent(getActivity(), BalanceActivity.class);
            startActivity(intent);

        } else if (v == sendBalance) {
            Intent intent = new Intent(getActivity(), SendActivity.class);
            startActivity(intent);
        } else if (v == imagerequest) {
            Intent intent = new Intent(getActivity(), RequestMoneyActivity.class);
            startActivity(intent);
        } else if (v == receiveMoney) {
            Intent intent = new Intent(getActivity(), ReceiveActivity.class);
            startActivity(intent);
        } else if (v == cardLoad) {
            Intent intent = new Intent(getActivity(), CardLoad.class);
            startActivity(intent);
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.changepin) {
            //Do something
            Intent intent = new Intent(MainFragment.this, ChangePin.class);
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
    }*/
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

