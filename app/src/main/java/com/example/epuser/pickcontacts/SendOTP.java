package com.example.epuser.pickcontacts;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by epuser on 5/22/2017.
 */

public class SendOTP extends Fragment implements View.OnClickListener {
    private EditText typeotp;
    private Button subotp;
    private TextView textotp;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.otp_send,container,false);
        typeotp =(EditText)view.findViewById(R.id.typeotp);
        subotp=(Button) view.findViewById(R.id.subotp);
        textotp=(TextView)view.findViewById(R.id.textotp);
        subotp.setOnClickListener(this);
        return  view;
    }

    @Override
    public void onClick(View v) {
        if(v==subotp){

            if(CheckNetwork.isInternetAvailable(getActivity())) {
                /*String otp = typeotp.getText().toString().replaceAll("\\s+", "");

                if (otp.length() > 9){
                    phone = phone.substring(phone.length() - 10);
                }
                else {
                    TFphoneNumber.setText(null);
                    TFamount.setText(null);
                    Toast.makeText(getActivity(), "Enter a valid Phone Number", Toast.LENGTH_LONG).show();
                    return;
                }*/
                String otp = typeotp.getText().toString();
               // String url = "http://192.168.10.93:8080/epcore/balance/Loader";
               String url="http://api.androidhive.info/contacts/";
                JSONObject data =new JSONObject();
                try {
                    data.put("HEADER", "FJGH");
                    JSONObject data1 = new JSONObject();
                    data1.put("mobNo",otp);
                    data1.put("reqAmount", 200);
                    data.put("DATA", data1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //create Jsonobject to be sent to backend
                new SendOTP.ReceiveAsyncTask().execute("POST", url, data.toString());
            }
            else
            {
                Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_LONG).show();
            }

        }

    }

    private class ReceiveAsyncTask extends AsyncTask<String,Void ,String>
    {

        @Override
        protected String doInBackground(String... params) {

            String result = Utils.makeRequestNGetResponse(params[0],params[1],params[2]);
            return result;
        }
        @Override
        protected void onPostExecute(String result)
        {

            Toast.makeText(getActivity(),result,Toast.LENGTH_LONG).show();
            String value = getArguments().getString("YourKey");
            if (value =="hey"){
                Intent intent=new Intent(getActivity(),MainActivity.class);
                startActivity(intent);

            }
            else
            {
                if (value =="LoginPage")
                    startActivity(new Intent(getActivity(),LoginPage.class));
            }

              // here we have to decide which activity to open
            //if success update sharedpreferences, fetch data , go to homepage
            //else try again logging in

        }
        }
    }

