package com.example.epuser.pickcontacts.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.epuser.pickcontacts.R;

public class BalanceActivity extends AppCompatActivity {
private TextView currentBalance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        currentBalance=(TextView)findViewById(R.id.currentbalance);
        showBalance();
    }

    private void showBalance() {



    }


}
