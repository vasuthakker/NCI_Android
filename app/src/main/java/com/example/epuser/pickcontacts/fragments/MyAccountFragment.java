package com.example.epuser.pickcontacts.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.activities.ChangePin;
import com.example.epuser.pickcontacts.activities.MainNavigationActivity;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;


/**
 * Created by epuser on 20-Jun-17.
 */

public class MyAccountFragment extends Fragment implements View.OnClickListener {
   private EditText ETFirstName  , ETMobile , ETEmail ;
   private TextView TVUpdateDetails , TVsave , TVImgName ;
    private MainNavigationActivity mainNavigationActivity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainNavigationActivity = (MainNavigationActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_account, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();

    }

    private void init() {
        ETFirstName = (EditText)getActivity().findViewById(R.id.ETFirstName);
        ETMobile = (EditText)getActivity().findViewById(R.id.ETMyAccMobile);
        ETEmail = (EditText)getActivity().findViewById(R.id.ETMyAccEmail);
        TVUpdateDetails = (TextView) getActivity().findViewById(R.id.TVUpdateDetails);
        TVsave = (TextView)getActivity().findViewById(R.id.TVMyAccSave);
        TVImgName = (TextView)getActivity().findViewById(R.id.TVMyAccImgName);
        TVUpdateDetails.setOnClickListener(this);
        TVsave.setOnClickListener(this);


        ETFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setImgName();

            }
        });

        ETFirstName.setText("Bittu Kumar");

        ETEmail.setText("bittu.dakshana15@gmail.com");
        ETMobile.setText("9987582933");

        setImgName();

    }



    @Override
    public void onClick(View v) {
        if(v==TVUpdateDetails)
        {

            ETFirstName.setEnabled(true);

            // ETMobile.setEnabled(true);
            ETEmail.setEnabled(true);
            TVsave.setVisibility(View.VISIBLE);
        }
        else if (v == TVsave)
        {

        }

    }
    private void setImgName() {
        String name = ETFirstName.getText().toString();
        String[] fullName = name.split("\\s+");
        String imgName ="";
        for (int i =0;i<Math.min(2,fullName.length);i++)
        {
            if (!(fullName[i].equals("")) && fullName[i]!=null ) {
                imgName = imgName.concat(fullName[i].substring(0, 1));
            }
        }
        imgName =  imgName.toUpperCase();
        TVImgName.setText(imgName);
    }
}
