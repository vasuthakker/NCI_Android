package com.example.epuser.pickcontacts.recyler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.epuser.pickcontacts.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by epuser on 6/16/2017.
 */

public class RecyclerAdapter extends ExpandableRecyclerViewAdapter<QuestionsTypeViewHolder, QuestionsViewHolder> {

    private android.support.v4.app.Fragment activity;

    public RecyclerAdapter(android.support.v4.app.Fragment activity, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.activity = activity;
    }

    @Override
    public QuestionsTypeViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
       LayoutInflater inflater = (LayoutInflater)activity.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.group_view_holder, parent, false);

        return new QuestionsTypeViewHolder(view);
    }

    @Override
    public QuestionsViewHolder onCreateChildViewHolder(ViewGroup parent, final int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.child_view_holder, parent, false);

        return new QuestionsViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(QuestionsViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Questions questions = ((QuestionType) group).getItems().get(childIndex);
        holder.onBind(questions,group);
    }

    @Override
    public void onBindGroupViewHolder(QuestionsTypeViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setGroupName(group);
    }
}
