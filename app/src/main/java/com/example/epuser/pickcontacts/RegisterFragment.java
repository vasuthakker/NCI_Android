package com.example.epuser.pickcontacts;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * Created by epuser on 5/20/2017.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener{

    private EditText regacnt,regphn,typeotp;
    private Button  btnregg,subotp;
    private PopupWindow pwindo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
        View view= inflater.inflate(R.layout.fragment_register,container,false);
        regacnt =(EditText)view.findViewById(R.id.regacnt);
        regphn=(EditText)view.findViewById(R.id.regphn);
        typeotp=(EditText) view.findViewById(R.id.typeotp);
        btnregg=(Button) view.findViewById(R.id.btnregg);
        subotp=(Button)view.findViewById(R.id.subotp);
        subotp.setOnClickListener(this);
        btnregg.setOnClickListener(this);
        typeotp.setVisibility(View.GONE);
        subotp.setVisibility(View.GONE);
        return  view;

    }

    @Override
    public void onClick(View v) {
        if(v==btnregg){
            if(CheckNetwork.isInternetAvailable(getActivity())){
                typeotp.setVisibility(View.VISIBLE);
                subotp.setVisibility(View.VISIBLE);
                regphn.setVisibility(View.GONE);
                regacnt.setVisibility(View.GONE);
                btnregg.setVisibility(View.GONE);


            }
            else {

                Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_SHORT).show();



            }
        }

    }
}
