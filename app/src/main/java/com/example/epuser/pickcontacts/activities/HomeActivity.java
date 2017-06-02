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
import android.widget.AdapterView;
import android.widget.Spinner;
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
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Transactions> DataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DataAdapter mAdapter;
    private TextView date;
    private Spinner spFilter;
    private static final String TAG = "HomeActivity";
    private TextView currentBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
        LoadTransactions(getDuration(0));

        spFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0)
                     LoadTransactions(getDuration(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        currentBalance.setText("999999999999");
    }



    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarnew);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // date=(TextView)findViewById(R.id.date);
        currentBalance=(TextView)findViewById(R.id.current_balance);
        spFilter = (Spinner)findViewById(R.id.home_spfilter);

        mAdapter = new DataAdapter(DataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        // date.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
//        if(v==date){
//
//           showDatePickerDialog();
//        }
    }


    private void LoadTransactions(String[] duration) {

        try {
            JSONObject requestJson = new JSONObject();
            JSONObject header = new JSONObject();
            JSONObject data = new JSONObject();
            requestJson.put(getString(R.string.header), header);

           // jsonObject2.put("mobileNumber", Preference.getStringPreference(this, AppConstants.MOBILE_NUMBER));
            data.put("mobileNumber", "9462025020");
            data.put("fromDate",duration[0]);
            data.put("toDate", duration[1]);
            data.put("ORDER_ID",System.currentTimeMillis());
            requestJson.put(getString(R.string.data), data);

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
                Log.e(TAG,"",e);
            }

        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getApplicationContext(), message);
        }
    };



    private void prepareData( JSONObject jsonObj) {

        Transactions transactions= new Transactions();

        try {
           JSONArray histories = jsonObj.getJSONArray("histories");
            for (int i = 0; i < histories.length(); i++) {
                JSONObject c = histories.getJSONObject(i);
                transactions.setTxnid(c.getDouble("txnid"));
                transactions.setTransectionrefno(c.getDouble(" transectionrefno"));
                transactions.setTxnamount(c.getDouble("txnamount"));
                transactions.setAmountpaid(c.getDouble("amountpaid"));
                transactions.setCharges(c.getDouble("charges"));
                transactions.setTxntime(c.getString(" txntime"));
                transactions.setTxnstatus(c.getString(" txnstatus"));
                transactions.setTxntype(c.getString("txntype"));
                transactions.setTxnreftype(c.getString("txnreftype"));
                transactions.setMobileno(c.getString(" mobileno"));
                transactions.setProxynumber(c.getString(" proxynumber"));
                transactions.setOrder_id(c.getString(" order_id"));

                DataList.add(transactions);
                mAdapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            Log.e(TAG,"",e);
        }

    }

    private String[] getDuration(int position) {
        String[] duration = new String[2] ;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if(position ==0)
        {
            duration[0] =duration[1] = Integer.toString(day) +"-" +Integer.toString(month) + "-" + Integer.toString(year);
            return duration;

        }
        if(position ==1)
        {
            calendar.add(Calendar.DAY_OF_MONTH,-1);
             year = calendar.get(Calendar.YEAR);
             month = calendar.get(Calendar.MONTH);
             day = calendar.get(Calendar.DAY_OF_MONTH);

            duration[0] =duration[1] = Integer.toString(day) +"-" +Integer.toString(month) + "-" + Integer.toString(year);
            return duration;
        }
        if (position==2)
        {
            duration[1] = Integer.toString(day) +"-" +Integer.toString(month) + "-" + Integer.toString(year);
            calendar.add(Calendar.DAY_OF_MONTH,-6);
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            duration[0] = Integer.toString(day) +"-" +Integer.toString(month) + "-" + Integer.toString(year);
            return duration;
        }
        if (position==3)
        {
            duration[1] = Integer.toString(day) +"-" +Integer.toString(month) + "-" + Integer.toString(year);
            duration[0]  = Integer.toString(1) +"-" +Integer.toString(month) + "-" + Integer.toString(year);

            return duration;

        }
        if (position==4)
        {
            duration[0] = "";
            duration[1] = Integer.toString(day) +"-" +Integer.toString(month) + "-" + Integer.toString(year);
            return duration;
        }
        return duration;
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePicker();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }




}
