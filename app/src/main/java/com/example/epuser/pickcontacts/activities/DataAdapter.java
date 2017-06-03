package com.example.epuser.pickcontacts.activities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.entities.Transactions;

import java.util.List;



public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private List<Transactions> DataList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView utility,id,number,amount,txn_time;

        public MyViewHolder(View view) {

            super(view);
            utility = (TextView) view.findViewById(R.id.utility);
            id = (TextView) view.findViewById(R.id.id);
            amount = (TextView) view.findViewById(R.id.amount);
            number=(TextView)view.findViewById(R.id.number);
            txn_time=(TextView)view.findViewById(R.id.txt_time);

        }
    }


    public DataAdapter(List<Transactions> DataList) {
        this.DataList = DataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Transactions transactions = DataList.get(position);


        holder.utility.setText(transactions.getTxnreftype());
        holder.id.setText(transactions.getTxnid().toString());
        holder.amount.setText(Double.toString(transactions.getTxnamount()));
        holder.number.setText(transactions.getTransectionrefno());
        holder.txn_time.setText(transactions.getTxntime());
    }

    @Override
    public int getItemCount() {
        return DataList.size();
    }
}
