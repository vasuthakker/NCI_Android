package com.example.epuser.pickcontacts.recyler;

import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.example.epuser.pickcontacts.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

/**
 * Created by epuser on 6/16/2017.
 */

public class OSViewHolder extends GroupViewHolder {
    private TextView osName;

    public OSViewHolder(View itemView) {
        super(itemView);

        osName = (TextView) itemView.findViewById(R.id.mobile_os);
    }

    @Override
    public void expand() {
       // osName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arrow, 0);
        Log.i("Adapter", "expand");
    }

    @Override
    public void collapse() {
        Log.i("Adapter", "collapse");
      //  osName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arrow, 0);
    }

    public void setGroupName(ExpandableGroup group) {
        osName.setText(group.getTitle());
    }
}
