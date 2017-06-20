package com.example.epuser.pickcontacts.recyler;

import android.view.View;
import android.widget.TextView;


import com.example.epuser.pickcontacts.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * Created by epuser on 6/16/2017.
 */

public class PhoneViewHolder extends ChildViewHolder {

    private TextView phoneName;

    public PhoneViewHolder(View itemView) {
        super(itemView);

        phoneName = (TextView) itemView.findViewById(R.id.phone_name);
    }

    public void onBind(Phone phone, ExpandableGroup group) {
        phoneName.setText(phone.getName());
        if (group.getTitle().equals("Android")) {
            phoneName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        } else if (group.getTitle().equals("iOS")) {
            phoneName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        } else {
            phoneName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }
}
