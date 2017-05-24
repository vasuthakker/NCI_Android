package com.example.epuser.pickcontacts;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by epuser on 5/20/2017.
 */

public class LoginFragment extends Fragment  implements View.OnClickListener{

    private EditText lgphn,lgpswrd;
    private Button btnlogg;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_login,container,false);

        lgphn =(EditText)view.findViewById(R.id.lgphn);
        lgpswrd=(EditText)view.findViewById(R.id.lgpswrd);
        btnlogg=(Button)view.findViewById(R.id.btnlogg);
        btnlogg.setOnClickListener(this);
        btnlogg.setOnClickListener(setClick("1"));


        return  view;

    }

    @Override
    public void onClick(View v) {
        if(v==btnlogg){



            if(CheckNetwork.isInternetAvailable(getActivity())) {

                String phone = lgphn.getText().toString().replaceAll("\\s+", "");

                if (phone.length() > 9){

                    phone = phone.substring(phone.length() - 10);
                }
                else {

                    lgphn.setText(null);
                    lgpswrd.setText(null);
                    Toast.makeText(getActivity(), "Enter a valid Phone Number", Toast.LENGTH_LONG).show();
                    return;
                }

                String loginPassword = lgpswrd.getText().toString();
               // String url = "http://192.168.10.93:8080/epcore/balance/Loader";
                String url = "http://api.androidhive.info/contacts/";

                JSONObject data = new JSONObject();
                try {
                    data.put("HEADER", "FJGH");
                    JSONObject data1 = new JSONObject();
                    data1.put("mobNo",phone);
                    data1.put("reqAmount", 200);
                    data.put("DATA", data1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                JsonObjectRequest jsonRequest = new JsonObjectRequest(url, data, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       // Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(getActivity(),MainActivity.class);
                        startActivity(intent);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
                );
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(jsonRequest);


                //create Jsonobject to be sent to backend
                //new LoginFragment.LoginAsyncTask().execute("POST", url, data.toString());
            }
            else
            {
                Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_LONG).show();
            }



        }
    }

    private View.OnClickListener setClick(final String value) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("value", "onClick: "+value);
            }
        };
    }

    private class LoginAsyncTask extends AsyncTask<String,Void ,String>
    {

        @Override
        protected String doInBackground(String... params) {

            String result = Utils.makeRequestNGetResponse(params[0],params[1],params[2]);
            return result;
        }
        @Override
        protected void onPostExecute(String result)
        {
            Intent intent= new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
              Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
            //if success update sharedpreferences, fetch data , go to homepage
            //else try again logging in

        }
    }
}




