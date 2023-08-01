package com.example.mstarttask_hussam.Model.utilits;

import static com.example.mstarttask_hussam.Model.utilits.AppConstants.Trace;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.mstarttask_hussam.Model.basic.MyApplication;

import java.util.Locale;


public class PreferencesUtils {

    private Activity activity;

    // Constructor
    public PreferencesUtils(Activity activity) {
        this.activity = activity;
    }

    public static void putString(String key, String val) {
        MyApplication.getInstance().getPreferences().edit().putString(key, val).apply();
        Trace("Saveing " + key, val + "");
    }

    public static String getString(String key, String val) {
        Trace("getString " + key, val + " Default");
        return MyApplication.getInstance().getPreferences().getString(key, val);
    }

    public static String getLanguage() {
        return PreferencesUtils.getString(PrefKeys.language, "en");
    }

    public static void setLanguage(String lang) {
        PreferencesUtils.set(PrefKeys.language, lang);
    }

    public static void setLocale(String lang, Activity activity) {
        Locale myLocale = new Locale(lang);
        Resources res = activity.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        activity.recreate();
    }

    public static void set(String key, String value) {
        MyApplication.getInstance().getPreferences().edit().putString(key, value).commit();
    }

}
