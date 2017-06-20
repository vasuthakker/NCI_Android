package com.example.epuser.pickcontacts.recyler;

import android.app.Fragment;
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
    private ArrayList<MobileOS> mobileOSes;
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
        ArrayList<Phone> iphones = new ArrayList<>();
        iphones.add(new Phone("iPhone 4"));
        iphones.add(new Phone("iPhone 4S"));
        iphones.add(new Phone("iPhone 5"));
        iphones.add(new Phone("iPhone 5S"));
        iphones.add(new Phone("iPhone 6"));
        iphones.add(new Phone("iPhone 6Plus"));
        iphones.add(new Phone("iPhone 6S"));
        iphones.add(new Phone("iPhone 6S Plus"));

        ArrayList<Phone> nexus = new ArrayList<>();
        nexus.add(new Phone("Nexus One"));
        nexus.add(new Phone("Nexus S"));
        nexus.add(new Phone("Nexus 4"));
        nexus.add(new Phone("Nexus 5"));
        nexus.add(new Phone("Nexus 6"));
        nexus.add(new Phone("Nexus 5X"));
        nexus.add(new Phone("Nexus 6P"));
        nexus.add(new Phone("Nexus 7"));

        ArrayList<Phone> windowPhones = new ArrayList<>();
        windowPhones.add(new Phone("Nokia Lumia 800"));
        windowPhones.add(new Phone("Nokia Lumia 710"));
        windowPhones.add(new Phone("Nokia Lumia 900"));
        windowPhones.add(new Phone("Nokia Lumia 610"));
        windowPhones.add(new Phone("Nokia Lumia 510"));
        windowPhones.add(new Phone("Nokia Lumia 820"));
        windowPhones.add(new Phone("Nokia Lumia 920"));

        mobileOSes.add(new MobileOS("iOS", iphones));
        mobileOSes.add(new MobileOS("Android", nexus));
        mobileOSes.add(new MobileOS("Window Phone", windowPhones));
    }

}
