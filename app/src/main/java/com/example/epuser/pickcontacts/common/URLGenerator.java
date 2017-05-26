package com.example.epuser.pickcontacts.common;

import com.example.epuser.pickcontacts.interfaces.URLProvider;

import java.net.URL;

/**
 * Created by Viral on 25-05-2017.
 */

public class URLGenerator implements URLProvider {

    public static final String BASE_ADDRESS_HOST = "http://192.168.10.86:8080/";
    public static final String URL_CONTEXT = BASE_ADDRESS_HOST + "epnci/";

   // http://localhost:8080/epnci/OtpGenerationStatus
    public static URLGenerator generator;

    public static URLGenerator getInstance() {
        if (generator == null)
            generator = new URLGenerator();
        return generator;
    }


    public static final String URL_LOGIN = "Loader";
    public static final String URL_OTP = "OtpGenerationStatus";



    @Override
    public String generateURL(String endPoint) {
        return URL_CONTEXT + endPoint;
    }
}