package com.example.epuser.pickcontacts.common;

import com.example.epuser.pickcontacts.activities.SendActivity;
import com.example.epuser.pickcontacts.interfaces.URLProvider;

import java.net.URL;

/**
 * Created by Viral on 25-05-2017.
 */

public class URLGenerator implements URLProvider {

    public static final String BASE_ADDRESS_HOST = "http://192.168.10.66:8081/";
    public static final String URL_CONTEXT = BASE_ADDRESS_HOST + "epnci/";



    public static URLGenerator generator;

    public static URLGenerator getInstance() {
        if (generator == null)
            generator = new URLGenerator();
        return generator;
    }
    public static final String URL_REGISTRATION_CHECK = "checkmobilenumber";
    public static final String URL_VERIFY_USER ="checkUserForOtp" ;
    public static final String URL_LOGIN = "loginDetails";
    public static final String URL_OTP = "OtpGenerationStatus";
    public static final String URL_OTP_VERIFICATION = "OtpVerifyStatus";
    public static final String URL_GET_SEC_QNS ="showSecQues" ;
    public static final String URL_CREATE_PIN_AND_SEC_QN = "accountSetup";
    public static final String URL_SAVE_SEC_ANS = "";

    public static final String URL_CHANGE_PIN = "PinChange";
    public static final String URL_GET_SEC_QN = "forgotPinShowSecurityQues";
    public static final String URL_SECURITY_ANS_VERIFICATION = "forgotPinGetSecurityAns";
    public static final String URL_CHANGE_FORGOT_PIN = "PinChangeWithSecQnA";

    public static final String URL_OTP_BENEFACTOR="as per url2";
    public static final String URL_CARD_LOAD="as per url3";
    public static final String  URL_FETCH_BAlANCE="checkBalance";

    public static final String URL_FETCH_TRANSACTIONS="wrest/txnhistory";
    public static final String URL_SEND = "";
    public static final String URL_PATIENTID="searchmobilewithpatientid";
    public static final String URL_FEEDBACK = "feedbackform";
    public static final String URL_FETCH_FAQ="according to URL";




    @Override
    public String generateURL(String endPoint) {
        return URL_CONTEXT + endPoint;
    }
}
