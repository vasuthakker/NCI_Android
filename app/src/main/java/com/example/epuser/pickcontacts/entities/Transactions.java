package com.example.epuser.pickcontacts.entities;

/**
 * Created by epuser on 5/31/2017.
 */

public class Transactions {



    private String txntime;



    private String txnstatus;
    private String txntype;
    private String txnreftype;
    private String mobileno;
    private String proxynumber;
    private String order_id;
    private String txnid;
    private String transectionrefno;
    private Double txnamount , amountpaid,charges;

    public Transactions() {
    }

    public Transactions(        String txnid,
                                String transectionrefno,
                               Double txnamount ,
                               Double amountpaid,
                              Double  charges,
                               String txntime,
                              String  txnstatus,
                              String  txntype ,
                              String  txnreftype,
                              String  mobileno,
                              String  proxynumber,
                               String order_id) {

        this.txnid=txnid;
        this.transectionrefno=transectionrefno;
        this.txnamount=txnamount;
        this.amountpaid=amountpaid;
        this.charges=charges;
        this.txntime=txntime;
        this.txnstatus=txnstatus;
        this.txntype=txntype;
        this.txnreftype=txnreftype;
        this.mobileno=mobileno;
        this.proxynumber=proxynumber;
        this.order_id=order_id;


    }

    public String getTxntime() {
        return txntime;
    }

    public void setTxntime(String txntime) {
        this.txntime = txntime;
    }

    public String getTxnstatus() {
        return txnstatus;
    }

    public void setTxnstatus(String txnstatus) {
        this.txnstatus = txnstatus;
    }

    public String getTxntype() {
        return txntype;
    }

    public void setTxntype(String txntype) {
        this.txntype = txntype;
    }

    public String getTxnreftype() {
        return txnreftype;
    }

    public void setTxnreftype(String txnreftype) {
        this.txnreftype = txnreftype;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getProxynumber() {
        return proxynumber;
    }

    public void setProxynumber(String proxynumber) {
        this.proxynumber = proxynumber;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }

    public String getTransectionrefno() {
        return transectionrefno;
    }

    public void setTransectionrefno(String transectionrefno) {
        this.transectionrefno = transectionrefno;
    }

    public Double getTxnamount() {
        return txnamount;
    }

    public void setTxnamount(Double txnamount) {
        this.txnamount = txnamount;
    }

    public Double getAmountpaid() {
        return amountpaid;
    }

    public void setAmountpaid(Double amountpaid) {
        this.amountpaid = amountpaid;
    }

    public Double getCharges() {
        return charges;
    }

    public void setCharges(Double charges) {
        this.charges = charges;
    }





}
