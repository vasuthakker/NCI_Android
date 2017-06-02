package com.example.epuser.pickcontacts.activities;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.common.URLGenerator;
import com.example.epuser.pickcontacts.common.Utils;
import com.example.epuser.pickcontacts.entities.Transactions;
import com.example.epuser.pickcontacts.exceptions.InternetNotAvailableException;
import com.example.epuser.pickcontacts.fragments.DatePicker;
import com.example.epuser.pickcontacts.network.VolleyJsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Transactions> DataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DataAdapter mAdapter;
    private TextView date;
    private static final String TAG = "HomeActivity";
    private TextView currentBalance;
    private long random=  System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarnew);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        date=(TextView)findViewById(R.id.date);
        currentBalance=(TextView)findViewById(R.id.current_balance);

        mAdapter = new DataAdapter(DataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        date.setOnClickListener(this);


        LoadTransactions();
    }




    @Override
    public void onClick(View v) {
        if(v==date){

           showDatePickerDialog();
        }
    }


    private void LoadTransactions() {

        try {
            JSONObject requestJson = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            requestJson.put("HEADER", jsonObject1);

           // jsonObject2.put("mobileNumber", Preference.getStringPreference(this, AppConstants.MOBILE_NUMBER));
            jsonObject2.put("mobileNumber", "9462025020");
            jsonObject2.put("fromDate","01-05-2017");
            jsonObject2.put("toDate", "30-05-2017");
            jsonObject2.put("ORDER_ID",random);
            requestJson.put("DATA", jsonObject2);

            VolleyJsonRequest.request(this, Utils.generateURL(URLGenerator.URL_FETCH_TRANSACTIONS), requestJson, CheckBalanceResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(this, getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private VolleyJsonRequest.OnJsonResponse CheckBalanceResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {


            try {
                JSONObject hello = jsonObj.getJSONObject("objectData");
                currentBalance.setText(hello.getString("balance"));
                prepareData(jsonObj);
            } catch (JSONException e) {
                Log.e(TAG,"error");
            }

        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getApplicationContext(), message);
        }
    };



    private void prepareData( JSONObject hello) {

        Transactions transactions= new Transactions();

        JSONArray histories = null;
        try {
            histories = hello.getJSONArray("histories");
            for (int i = 0; i < histories.length(); i++) {
                JSONObject c = histories.getJSONObject(i);
                Double txnid = c.getDouble("txnid");
                Double  transectionrefno = c.getDouble(" transectionrefno");
                Double   txnamount=c.getDouble("txnamount");
                Double  amountpaid=c.getDouble("amountpaid");
                Double  charges=c.getDouble("charges");
                String  txntime = c.getString(" txntime");
                String  txnstatus = c.getString(" txnstatus");
                String  txntype = c.getString("txntype");
                String  txnreftype = c.getString("txnreftype");
                String  mobileno = c.getString(" mobileno");
                String proxynumber = c.getString(" proxynumber");
                String  order_id = c.getString(" order_id");

                transactions = new Transactions( txnid,transectionrefno,txnamount ,amountpaid,charges,txntime,txnstatus,txntype ,txnreftype,mobileno,proxynumber,order_id);
                DataList.add(transactions);
                mAdapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePicker();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


}
