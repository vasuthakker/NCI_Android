package com.example.epuser.pickcontacts.common;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epuser.pickcontacts.R;
import com.example.epuser.pickcontacts.interfaces.URLProvider;

/**
 * Created by Viral on 25-05-2017.
 */

public class Utils {


    private static View customToastView;

    public static String generateURL(String endPoint) {
        URLProvider provider = URLGenerator.getInstance();
        return provider.generateURL(endPoint);
    }

    public static void showToast(Context context, String message) {
        if (context != null) {
            if (customToastView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                customToastView = inflater.inflate(R.layout.toastview, null);
            }
            TextView textView = (TextView) customToastView.findViewById(R.id.toast_txtview);
            textView.setText(message);
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setView(customToastView);
            toast.show();
        }
    }

}
