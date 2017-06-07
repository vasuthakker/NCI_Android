package com.example.epuser.pickcontacts.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.common.URLGenerator;
import com.example.epuser.pickcontacts.common.Utils;
import com.example.epuser.pickcontacts.entities.Transactions;
import com.example.epuser.pickcontacts.exceptions.InternetNotAvailableException;
import com.example.epuser.pickcontacts.network.VolleyJsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import static android.R.attr.duration;
import static android.R.attr.switchMinWidth;
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int TODAY = 0;
    private static final int YESTERDAY = 1;
    private static final int LAST_7_DAYS = 2;
    private static final int THIS_MONTH = 3;
    private static final int TILL_NOW = 4;

    private List<Transactions> DataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DataAdapter mAdapter;
    private Spinner spFilter;
    private static final String TAG = "HomeActivity";
    private TextView currentBalance, noRecords, signout;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private String fromDate, toDate;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Calendar cal = Calendar.getInstance();
        fromDate = toDate = formatter.format(cal.getTimeInMillis());

        LoadTransactions();
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();

        spFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Calendar calendar = Calendar.getInstance();
                toDate = formatter.format(calendar.getTimeInMillis());
                switch (position) {
                    case TODAY:
                        fromDate = toDate;
                        break;
                    case YESTERDAY:
                        calendar.add(Calendar.DAY_OF_YEAR, -1);
                        fromDate = toDate = formatter.format(calendar.getTimeInMillis());
                        break;
                    case LAST_7_DAYS:
                        calendar.add(Calendar.DAY_OF_YEAR, -7);
                        fromDate = formatter.format(calendar.getTimeInMillis());
                        break;
                    case THIS_MONTH:
                        calendar.add(Calendar.MONTH, -1);
                        fromDate = formatter.format(calendar.getTimeInMillis());
                        break;
                    case TILL_NOW:
                        fromDate = "";
                        break;
                }
                LoadTransactions();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void init() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.home_swipelayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadTransactions();
            }
        });
        signout = (TextView) findViewById(R.id.signout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarnew);
        setSupportActionBar(toolbar);
        noRecords = (TextView) findViewById(R.id.no_record);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        currentBalance = (TextView) findViewById(R.id.current_balance);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        LoadTransactions();

         signout.setOnClickListener(this);
        String[] filterList=getResources().getStringArray(R.array.home_date_filter);

        signout.setOnClickListener(this);
        String[] filterList = getResources().getStringArray(R.array.home_date_filter);
        ArrayAdapter spAdapter = new ArrayAdapter<String>(HomeActivity.this, R.layout.home_filter_item, filterList);
        spAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spFilter.setAdapter(spAdapter);

    }


    @Override
    public void onClick(View v) {
        if (v == signout) {
            Intent intent = new Intent(HomeActivity.this, LoginPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        }
    }


    private void LoadTransactions() {

        try {
            JSONObject requestJson = new JSONObject();
            JSONObject header = new JSONObject();
            JSONObject data = new JSONObject();
            requestJson.put(getString(R.string.header), header);

            // data.put("mobileNumber", Preference.getStringPreference(this, AppConstants.MOBILE_NUMBER));
            // TODO: 6/5/2017  generalise for the number entered by the user
            data.put("mobileNumber", "9462025020");
            data.put("fromDate", fromDate);
            data.put("toDate", toDate);
            data.put(getString(R.string.Order_id), System.currentTimeMillis());
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
                JSONObject data = jsonObj.getJSONObject("DATA");
                currentBalance.setText(data.getString("balance"));
                prepareData(data);
            } catch (JSONException e) {
                Log.e(TAG, "", e);
            }

        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getApplicationContext(), message);
        }
    };


    private void prepareData(JSONObject jsonObj) {
        List<Transactions> DataList = new ArrayList<>();
        Transactions transactions;
        try {
            JSONArray histories = jsonObj.getJSONArray("histories");
            Log.v(TAG, "History is:" + histories.toString());
            if (histories.length() == 0) {

                noRecords.setVisibility(View.VISIBLE);
            } else {
                noRecords.setVisibility(View.GONE);
                for (int i = histories.length() - 1; i >= 0; i--) {


                    transactions = new Transactions();
                    JSONObject c = histories.getJSONObject(i);
                    transactions.setTxnid(c.getString("txnid"));
                    transactions.setTransectionrefno(c.getString("transectionrefno"));
                    transactions.setTxnamount(c.getDouble("txnamount"));
                    transactions.setAmountpaid(c.getDouble("amountpaid"));
                    transactions.setCharges(c.getDouble("charges"));
                    transactions.setTxntime(c.getString("txntime"));
                    transactions.setTxnstatus(c.getString("txnstatus"));
                    transactions.setTxntype(c.getString("txntype"));
                    transactions.setTxnreftype(c.getString("txnreftype"));
                    transactions.setMobileno(c.getString("mobileno"));
                    transactions.setProxynumber(c.getString("proxynumber"));
                    transactions.setOrder_id(c.getString("order_id"));

                    DataList.add(transactions);
                }

            }

            mAdapter = new DataAdapter(DataList);
            recyclerView.setAdapter(mAdapter);

        } catch (JSONException e) {
            Log.e(TAG, "", e);
        }

    }


}
