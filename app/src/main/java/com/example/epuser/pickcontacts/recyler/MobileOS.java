package com.example.epuser.pickcontacts.recyler;

import android.annotation.SuppressLint;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by epuser on 6/16/2017.
 */
@SuppressLint("ParcelCreator")
public class MobileOS extends ExpandableGroup<Phone> {

    public MobileOS(String title, List<Phone> items) {
        super(title, items);
    }
}
