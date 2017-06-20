package com.example.epuser.pickcontacts.entities;

/**
 * Created by epuser on 6/15/2017.
 */

public class PatientID {
    private String patientId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String firstName,middleName,lastName;

    public PatientID(){

    }

    public PatientID(String patientID,String firstName,String middleName,String lastName){
        this.patientId=patientID;
        this.firstName=firstName;
        this.middleName=middleName;
        this.lastName=lastName;


    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }





}
