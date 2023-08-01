package com.example.mstarttask_hussam.Views.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.NonNull;

import com.example.mstarttask_hussam.Model.utilits.PreferencesUtils;
import com.example.mstarttask_hussam.R;

import java.util.Locale;

public class LanguageDialog extends Dialog {

    private final Context context;
    private final Activity activity;
    private Button apply;
    private RadioButton english, arabic;

    public LanguageDialog(@NonNull Context context, Activity activity) {
        super(context);
        this.context = context;
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_language);

        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.setCancelable(true);

        initials();
        clicks();

    }

    private void clicks() {
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PreferencesUtils.getLanguage().equalsIgnoreCase("en") && arabic.isChecked()){
                    switchLanguage("ar");
                } else if (PreferencesUtils.getLanguage().equalsIgnoreCase("ar") && english.isChecked()){
                    switchLanguage("en");
                } else {
                    dismiss();
                }
            }
        });
    }

    private void initials() {
        apply = findViewById(R.id.apply_button);

        arabic = findViewById(R.id.arabic);
        english = findViewById(R.id.english);

        if (PreferencesUtils.getLanguage().equalsIgnoreCase("en")){
            english.setChecked(true);
        } else {
            arabic.setChecked(true);
        }

    }

    private void switchLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.locale = locale;

        if (languageCode.equals("ar")) {
            configuration.setLayoutDirection(new Locale(languageCode));
        } else {
            configuration.setLayoutDirection(Locale.getDefault());
        }

        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());

        PreferencesUtils.setLanguage(languageCode);
        Intent intent = activity.getIntent();
        activity.finish();
        activity.startActivity(intent);

        dismiss();
    }

}
