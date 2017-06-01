package com.example.epuser.pickcontacts.common;

import com.example.epuser.pickcontacts.activities.SendActivity;
import com.example.epuser.pickcontacts.interfaces.URLProvider;

import java.net.URL;

/**
 * Created by Viral on 25-05-2017.
 */

public class URLGenerator implements URLProvider {

    public static final String BASE_ADDRESS_HOST = "http://192.168.10.60:8080/";
    public static final String URL_CONTEXT = BASE_ADDRESS_HOST + "epnci/";




    // http://localhost:8080/epnci/OtpGenerationStatus
    //http://localhost:8080/epnci/OtpVerifyStatus
    public static URLGenerator generator;

    public static URLGenerator getInstance() {
        if (generator == null)
            generator = new URLGenerator();
        return generator;
    }


    public static final String URL_LOGIN = "Loader";
    public static final String URL_OTP = "OtpGenerationStatus";
    public static final String URL_OTP_VERIFICATION = "OtpVerifyStatus";
    public static final String URL_CREATE_PIN = "pinSetup";
    public static final String URL_PIN_VERIFICATION="loginStatus";
    public static final String URL_CHANGE_PIN = "pinChange";
    public static final String URL_GET_SEC_QNS ="securityQnA" ;
    public static final String URL_SECURITY_ANS_VERIFICATION = "";

    public static final String URL_OTP_BENEFACTOR="as per url2";
    public static final String URL_CARD_LOAD="as per url3";
    public static final String  URL_FETCH_BAlANCE="checkBalance";

    public static final String   URL_FETCH_TRANSACTIONS="txnhistory";




    @Override
    public String generateURL(String endPoint) {
        return URL_CONTEXT + endPoint;
    }
}
