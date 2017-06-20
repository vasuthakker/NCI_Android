package com.example.epuser.pickcontacts.entities;

/**
 * Created by epuser on 6/15/2017.
 */

public class PatientID {
    private String patientId;
    private String patientName;

    public PatientID(){

    }

    public PatientID(String patientID,String patientName){
        this.patientId=patientID;
        this.patientName=patientName;

    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientnName() {
        return patientName;
    }

    public void setPatientnName(String patientnName) {
        this.patientName = patientnName;
    }



}
