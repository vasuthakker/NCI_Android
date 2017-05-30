package com.example.epuser.pickcontacts.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.epuser.pickcontacts.R;


public class ForgotSetPinFragment extends Fragment  implements View.OnClickListener{
    private EditText pinafterForgot,confirmPinForgot;
    private Button submitpinForgot;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_forgot_set_pin,container,false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {

        pinafterForgot =(EditText)getActivity().findViewById(R.id.set_pin_forgot);
        confirmPinForgot=(EditText)getActivity().findViewById(R.id.confirm_pin_forgot);
        submitpinForgot=(Button)getActivity().findViewById(R.id.sub_set_pin_forgot);
        submitpinForgot.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==submitpinForgot){


        }
    }
}
