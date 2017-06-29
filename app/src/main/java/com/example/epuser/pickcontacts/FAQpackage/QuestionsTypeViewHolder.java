package com.example.epuser.pickcontacts.FAQpackage;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.epuser.pickcontacts.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

/**
 * Created by epuser on 6/16/2017.
 */

public class QuestionsTypeViewHolder extends GroupViewHolder {
    private TextView osName;
    private ImageView imagePosi, imageNegi;

    public QuestionsTypeViewHolder(View itemView) {
        super(itemView);

        osName = (TextView) itemView.findViewById(R.id.mobile_os);
        imageNegi=(ImageView)itemView.findViewById(R.id.image_sign);
        imagePosi=(ImageView)itemView.findViewById(R.id.image_nega);
    }

    @Override
    public void expand() {
     //  osName.setCompoundDrawablesWithIntrinsicBounds( R.drawable.addition,0 , 0, 0);
        imagePosi.setVisibility(View.GONE);
        imageNegi.setVisibility(View.VISIBLE);
        Log.i("Adapter", "expand");
    }

    @Override
    public void collapse() {
        imagePosi.setVisibility(View.VISIBLE);
        imageNegi.setVisibility(View.GONE);
        Log.i("Adapter", "collapse");
       // osName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.subtraction, 0, 0, 0);
    }

    public void setGroupName(ExpandableGroup group) {
        osName.setText(group.getTitle());
    }
}
