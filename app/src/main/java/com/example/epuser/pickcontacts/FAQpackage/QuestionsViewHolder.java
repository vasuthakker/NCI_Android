package com.example.epuser.pickcontacts.FAQpackage;

import android.view.View;
import android.widget.TextView;


import com.example.epuser.pickcontacts.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * Created by epuser on 6/16/2017.
 */

public class QuestionsViewHolder extends ChildViewHolder {

    private TextView question_number,answernumber;

    public QuestionsViewHolder(View itemView) {
        super(itemView);

        question_number = (TextView) itemView.findViewById(R.id.question_number);
        answernumber=(TextView)itemView.findViewById(R.id.answer_number);

    }

    public void onBind(Questions questions, ExpandableGroup group) {
        question_number.setText(questions.getQuery());
        answernumber.setText(questions.getSolution());
        question_number.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

    }
}
