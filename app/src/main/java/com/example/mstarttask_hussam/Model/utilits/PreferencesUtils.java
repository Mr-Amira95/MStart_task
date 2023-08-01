package com.example.mstarttask_hussam.Model.utilits;

import android.app.Activity;
import com.example.mstarttask_hussam.Model.basic.MyApplication;

public class PreferencesUtils {

    private Activity activity;

    // Constructor
    public PreferencesUtils(Activity activity) {
        this.activity = activity;
    }

    public static String getLanguage() {
        return MyApplication.getInstance().getPreferences().getString(PrefKeys.language, "en");
    }

    public static void setLanguage(String lang) {
        MyApplication.getInstance().getPreferences().edit().putString(PrefKeys.language, lang).commit();
    }

}
