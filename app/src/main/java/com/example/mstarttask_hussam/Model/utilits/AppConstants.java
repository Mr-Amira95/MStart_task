package com.example.mstarttask_hussam.Model.utilits;

import android.util.Log;

import com.intuit.sdp.BuildConfig;

public class AppConstants {

    public static final String BASE_URL = "http://api.aladhan.com/v1/";

    public static final String From_G_To_H_URL = "gToH";
    public static final int From_G_To_H_TAG = 1;

    public static void Trace(String tag, String msg) {

        if (BuildConfig.DEBUG) {
            Log.d(tag + "", msg + "\n");
        }
    }

}