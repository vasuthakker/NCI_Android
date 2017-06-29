package com.example.epuser.pickcontacts.FAQpackage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.common.AppConstants;
import com.example.epuser.pickcontacts.common.Preference;
import com.example.epuser.pickcontacts.common.URLGenerator;
import com.example.epuser.pickcontacts.common.Utils;
import com.example.epuser.pickcontacts.exceptions.InternetNotAvailableException;
import com.example.epuser.pickcontacts.network.VolleyJsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by epuser on 6/16/2017.
 */

public class FaqFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "FaqFragment";
    private RecyclerView recyclerView;
    private ArrayList<QuestionType> quesTypeList;
    private RecyclerAdapter adapter;
    private TextView nofaqs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_faq, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        nofaqs=(TextView)getActivity().findViewById(R.id.no_Faqs);
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.recycler_view);
        quesTypeList = new ArrayList<>();
        FetchData();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapter(this, quesTypeList);
        recyclerView.setAdapter(adapter);
    }

    private void FetchData() {


        try {
            JSONObject requestJson = new JSONObject();
            JSONObject header = new JSONObject();
            JSONObject data = new JSONObject();
            requestJson.put(getString(R.string.header), header);

            // data.put("mobileNumber", Preference.getStringPreference(this, AppConstants.MOBILE_NUMBER));
            // TODO: 6/5/2017  generalise for the number entered by the user
            requestJson.put(getString(R.string.data), data);

            VolleyJsonRequest.request(getActivity(), Utils.generateURL(URLGenerator.URL_FETCH_FAQ), requestJson, FaqResp, true);
        } catch (JSONException e) {
            Log.e(TAG, "validateReceiveMoney: JSONException", e);
        } catch (InternetNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private VolleyJsonRequest.OnJsonResponse FaqResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {


            try {
                JSONArray data = jsonObj.getJSONArray("DATA");
               // .setText(data.getString("balance"));
                setData(data);

            } catch (JSONException e) {
                Log.e(TAG, "", e);
            }

        }

        @Override
        public void errorReceived(int code, String message) {
            Utils.showToast(getActivity(), message);
        }
    };


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        adapter.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
       super.onActivityCreated(savedInstanceState);
       // adapter.onRestoreInstanceState(savedInstanceState);
    }

    private void setData(JSONArray jsonArray) {
        ArrayList<Questions> questionsList = new ArrayList<>();
       // ArrayList<QuestionType> quesTypeList = new ArrayList<>();


        try {
            //JSONArray quesCategory = jsonArray.getJSONArray("QuestionType");
            Log.v(TAG, "jsonArray is:" + jsonArray.toString());
            if (jsonArray.length() == 0) {

                nofaqs.setVisibility(View.VISIBLE);
            } else {
                nofaqs.setVisibility(View.GONE);
                for (int i =0; i < jsonArray.length(); i++) {
                    JSONObject qt = jsonArray.getJSONObject(i);

                    JSONArray quest= qt.getJSONArray("questions");


                     for (int j =0; j < quest.length(); j++){
                         JSONObject quesj = quest.getJSONObject(j);
                         questionsList.add(new Questions(quesj.getString("questionj"),quesj.getString("answerj")));
                     }
                    quesTypeList.add( new QuestionType( qt.getString("quesCategoryi"), questionsList));

                }

            }
        } catch (JSONException e) {
            Log.e(TAG, "", e);
        }













//
//                ArrayList<Questions> wallet = new ArrayList<>();
//        wallet.add(new Questions("iPhone 4"));
//        wallet.add(new Questions("iPhone 4S"));
//        wallet.add(new Questions("iPhone 5"));
//        wallet.add(new Questions("iPhone 5S"));
//        wallet.add(new Questions("iPhone 6"));
//        wallet.add(new Questions("iPhone 6Plus"));
//        wallet.add(new Questions("iPhone 6S"));
//        wallet.add(new Questions("iPhone 6S Plus"));
//
//        ArrayList<Questions> nexus = new ArrayList<>();
//        nexus.add(new Questions("Nexus One"));
//        nexus.add(new Questions("Nexus S"));
//        nexus.add(new Questions("Nexus 4"));
//        nexus.add(new Questions("Nexus 5"));
//        nexus.add(new Questions("Nexus 6"));
//        nexus.add(new Questions("Nexus 5X"));
//        nexus.add(new Questions("Nexus 6P"));
//        nexus.add(new Questions("Nexus 7"));
//
//        ArrayList<Questions> windowQuestionses = new ArrayList<>();
//        windowQuestionses.add(new Questions("Nokia Lumia 800"));
//        windowQuestionses.add(new Questions("Nokia Lumia 710"));
//        windowQuestionses.add(new Questions("Nokia Lumia 900"));
//        windowQuestionses.add(new Questions("Nokia Lumia 610"));
//        windowQuestionses.add(new Questions("Nokia Lumia 510"));
//        windowQuestionses.add(new Questions("Nokia Lumia 820"));
//        windowQuestionses.add(new Questions("Nokia Lumia 920"));
//
//        mobileOSes.add(new QuestionType("wallet", wallet));
//        mobileOSes.add(new QuestionType("Android", nexus));
//        mobileOSes.add(new QuestionType("Window Questions", windowQuestionses));
    }

}
