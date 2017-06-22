package com.example.epuser.pickcontacts.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.epuser.pickcontacts.R;

/**
 * Created by epuser on 6/16/2017.
 */

public class FeedbackFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feedback, container, false);


    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {
    }
}
