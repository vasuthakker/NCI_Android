package com.example.epuser.pickcontacts.recyler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.epuser.pickcontacts.R;

import java.util.ArrayList;

/**
 * Created by epuser on 6/16/2017.
 */

public class FaqFragment extends android.support.v4.app.Fragment {

    private RecyclerView recyclerView;
    private ArrayList<QuestionType> mobileOSes;
    private RecyclerAdapter adapter;

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
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.recycler_view);
        mobileOSes = new ArrayList<>();

        setData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapter(this, mobileOSes);
        recyclerView.setAdapter(adapter);
    }

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

    private void setData() {
        ArrayList<Questions> wallet = new ArrayList<>();
        wallet.add(new Questions("iPhone 4"));
        wallet.add(new Questions("iPhone 4S"));
        wallet.add(new Questions("iPhone 5"));
        wallet.add(new Questions("iPhone 5S"));
        wallet.add(new Questions("iPhone 6"));
        wallet.add(new Questions("iPhone 6Plus"));
        wallet.add(new Questions("iPhone 6S"));
        wallet.add(new Questions("iPhone 6S Plus"));

        ArrayList<Questions> nexus = new ArrayList<>();
        nexus.add(new Questions("Nexus One"));
        nexus.add(new Questions("Nexus S"));
        nexus.add(new Questions("Nexus 4"));
        nexus.add(new Questions("Nexus 5"));
        nexus.add(new Questions("Nexus 6"));
        nexus.add(new Questions("Nexus 5X"));
        nexus.add(new Questions("Nexus 6P"));
        nexus.add(new Questions("Nexus 7"));

        ArrayList<Questions> windowQuestionses = new ArrayList<>();
        windowQuestionses.add(new Questions("Nokia Lumia 800"));
        windowQuestionses.add(new Questions("Nokia Lumia 710"));
        windowQuestionses.add(new Questions("Nokia Lumia 900"));
        windowQuestionses.add(new Questions("Nokia Lumia 610"));
        windowQuestionses.add(new Questions("Nokia Lumia 510"));
        windowQuestionses.add(new Questions("Nokia Lumia 820"));
        windowQuestionses.add(new Questions("Nokia Lumia 920"));

        mobileOSes.add(new QuestionType("wallet", wallet));
        mobileOSes.add(new QuestionType("Android", nexus));
        mobileOSes.add(new QuestionType("Window Questions", windowQuestionses));
    }

}
