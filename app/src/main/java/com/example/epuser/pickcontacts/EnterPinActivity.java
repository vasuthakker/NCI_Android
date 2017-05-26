package com.example.epuser.pickcontacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EnterPinActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView forgotPin;
    private EditText ETPin;
    private Button submitPinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pin);

        init();
    }

    private void init() {
        forgotPin =(TextView)findViewById(R.id.forgotPin);
        ETPin = (EditText)findViewById(R.id.ETPin);
        submitPinButton = (Button)findViewById(R.id.submitPinButton);
    }


    @Override
    public void onClick(View v) {
        if(v== submitPinButton)
        {
            validatePin();
        }
        if(v==forgotPin)
        {

        }
    }

    private void validatePin()
    {

    }
}
