package com.example.epuser.pickcontacts.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.common.AppConstants;
import com.example.epuser.pickcontacts.common.Preference;
import com.example.epuser.pickcontacts.common.URLGenerator;
import com.example.epuser.pickcontacts.common.Utils;
import com.example.epuser.pickcontacts.exceptions.InternetNotAvailableException;
import com.example.epuser.pickcontacts.network.VolleyJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePin extends AppCompatActivity {
    private static final String TAG = "ChangePin";
    private EditText oldPinET,newPinET,confirmPinET;
    private Button changePinbtn;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin);
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
        changePinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePin();
            }
        });

        oldPinET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (oldPinET.length()==4)
                {
                    //oldPinET.clearFocus();
                    newPinET.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (oldPinET.length()!=4)
//                {
//                    oldPinET.requestFocus();
//                    oldPinET.setError(getString(R.string.enter_valid_pin));
//                }

            }
        });

        newPinET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (newPinET.length() == 4)
                {
                   // newPinET.clearFocus();
                    confirmPinET.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void init() {
        oldPinET = (EditText)findViewById(R.id.old_pin);
        oldPinET.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        newPinET = (EditText)findViewById(R.id.new_pin);
        newPinET.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        confirmPinET = (EditText)findViewById(R.id.confirm_new_pin);
        confirmPinET.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        changePinbtn = (Button)findViewById(R.id.newpinbutton);
        toolbar  = (Toolbar)findViewById(R.id.changePinToolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void changePin() {
        String oldPin = oldPinET.getText().toString();
        String newPin = newPinET.getText().toString();
        String confirmNewPin = confirmPinET.getText().toString();
        if (oldPin.length()!=4)
        {
            oldPinET.setError(getString(R.string.enter_valid_pin));
            return;
        }
        if (newPin.length()!=4)
        {
            newPinET.setError(getString(R.string.enter_valid_pin));
            return;
        }
        if (newPin.equals(oldPin))
        {
            newPinET.setError(getString(R.string.different_old_and_new_pins));
            return;
        }
        if (!(newPin.equals(confirmNewPin)))
        {
            confirmPinET.setError(getString(R.string.pins_dont_match));
            return;
        }

        try {
            JSONObject requestJson = new JSONObject();
            JSONObject header = new JSONObject();
            JSONObject data = new JSONObject();
            requestJson.put(getString(R.string.header), header);
            data.put(getString(R.string.mobile_number), Preference.getStringPreference(ChangePin.this,AppConstants.MOBILE_NUMBER));
            data.put("mOldPin",oldPin);
            data.put("mNewPin",newPin);
            requestJson.put(getString(R.string.data), data);

            VolleyJsonRequest.request(ChangePin.this, Utils.generateURL(URLGenerator.URL_CHANGE_PIN), requestJson, changePinResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(ChangePin.this, getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }


    }
    private VolleyJsonRequest.OnJsonResponse changePinResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
            Utils.showSuccessToast(ChangePin.this,"Pin successfully changed!");
//                    Intent intent = new Intent(ChangePin.this,MainNavigationActivity.class);
//                    startActivity(intent);
            onBackPressed();
            ChangePin.this.finish();
        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(ChangePin.this, message);
            oldPinET.setText(null);
            newPinET.setText(null);
            confirmPinET.setText(null);
            oldPinET.requestFocus();
        }
    };
}
