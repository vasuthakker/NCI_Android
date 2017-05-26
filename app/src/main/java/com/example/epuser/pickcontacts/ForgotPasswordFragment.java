package com.example.epuser.pickcontacts;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ForgotPasswordFragment extends Fragment implements View.OnClickListener {
    private EditText forgotphn;
    private Button btnotp;
    private TextView textphone;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_forgot,container,false);

        forgotphn=(EditText)view.findViewById(R.id.forgotphn);
        btnotp=(Button)view.findViewById(R.id.btnotp);

        textphone=(TextView)view.findViewById(R.id.textphone);

        btnotp.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View v) {
        if(v == btnotp){
            FragmentManager manager = getFragmentManager();
            SendOTP sendOTP = new SendOTP();
            Bundle args = new Bundle();
            args.putString("YourKey", "LoginPage");
            sendOTP.setArguments(args);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.lgcontainer, sendOTP, "sendOTP");
            transaction.commit();



    }
    }
}
