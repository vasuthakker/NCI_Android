package com.example.epuser.pickcontacts.FAQpackage;

import android.annotation.SuppressLint;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by epuser on 6/16/2017.
 */
@SuppressLint("ParcelCreator")
public class QuestionType extends ExpandableGroup<Questions> {



    public QuestionType(String title, List<Questions> items) {
        super(title, items);
    }
}
