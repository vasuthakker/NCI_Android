package com.example.epuser.pickcontacts;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by epuser on 5/26/2017.
 */

public class CreatePinFragment extends Fragment {
    private EditText pin_ET , confirm_pin_ET;
    private Button create_pin_btn;
    private LoginPage loginActivity;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_create_pin, container, false);

    }
    private void init() {
        pin_ET = (EditText)getActivity().findViewById(R.id.pin_ET);
        confirm_pin_ET = (EditText)getActivity().findViewById(R.id.confirm_pin_ET);
        create_pin_btn = (Button)getActivity().findViewById(R.id.create_pin_btn);


    }

    @Override
    public void onStart() {
        super.onStart();
        init();

        create_pin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPin();
            }
        });
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivity = (LoginPage) context;
    }

    private void createPin() {
        String pin = pin_ET.getText().toString();
        if (pin.equals(confirm_pin_ET.getText().toString()))
        {
            if(pin.length()==4)
            {
                // TODO: 5/26/2017  save in sharedpreferences
                loginActivity.changeFragment(new LoginFragment());


            }
            else
            {
                pin_ET.setError("Please enter a four digit pin");
            }

        }
        else
        {
            confirm_pin_ET.setError("pins did not match");
            return;
        }
    }


}
