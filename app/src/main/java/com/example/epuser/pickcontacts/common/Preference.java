package com.example.epuser.pickcontacts.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ADMIN on 7/19/2016.
 */
public class Preference {

    private static final String SHARED_PREF = "NCI_APP";
    private static SharedPreferences pref;

    public static void savePreference(Context context, String key, String value) {
        if (pref == null)
            pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void clearPreference(Context context, String key) {
        if (pref == null)
            pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.apply();
    }

    public static void savePreference(Context context, String key, int value) {
        if (pref == null)
            pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void savePreference(Context context, String key, boolean value) {
        if (pref == null)
            pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static String getStringPreference(Context context, String key) {
        if (pref == null)
            pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        return pref.getString(key, null);

    }

    public static int getIntPreference(Context context, String key) {
        if (pref == null)
            pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        return pref.getInt(key, 0);

    }

    public static boolean getBooleanPreference(Context context, String key) {
        if (pref == null)
            pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        return pref.getBoolean(key, false);

    }

    public static void clearAll(Context context) {
        if (pref == null)
            pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}
