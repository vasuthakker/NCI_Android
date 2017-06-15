package com.example.epuser.pickcontacts.activities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.entities.PatientID;

import java.util.List;

/**
 * Created by epuser on 6/15/2017.
 */

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.MyViewHolder> {
    private List<PatientID> DataList;



    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView patient_id,patient_name;

        public MyViewHolder(View view) {

            super(view);
            patient_id=(TextView)view.findViewById(R.id.patient_id);
            patient_name = (TextView) view.findViewById(R.id.patient_name);


        }
    }


    public PatientAdapter(List<PatientID> DataList) {
        this.DataList = DataList;

    }

    @Override
    public PatientAdapter.MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dialog_patient_id, parent, false);

        return new PatientAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PatientAdapter.MyViewHolder holder, int position) {
        PatientID patientId = DataList.get(position);


        holder.patient_id.setText(patientId.getPatientId());
        holder.patient_name.setText(patientId.getPatientnName());
    }

    @Override
    public int getItemCount() {
        return DataList.size();
    }



}
