package com.example.mstarttask_hussam.Views.Activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mstarttask_hussam.Model.utilits.PreferencesUtils;
import com.example.mstarttask_hussam.R;
import com.example.mstarttask_hussam.Views.Dialogs.ConfirmationDialog;
import com.example.mstarttask_hussam.Views.Dialogs.LanguageDialog;
import com.example.mstarttask_hussam.Views.Fragments.MainFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ImageView language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchLanguage();
        initials();
        clicks();

    }

    private void initials() {

        language = findViewById(R.id.language);
        setDefaultFragment(new MainFragment());
    }

    private void clicks() {

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LanguageDialog languageDialog = new LanguageDialog(MainActivity.this, MainActivity.this);
                languageDialog.show();
            }
        });
    }

    private void setDefaultFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame ,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0){
            ConfirmationDialog confirmationDialog = new ConfirmationDialog(this, "exit", -1);
            confirmationDialog.show();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    private void switchLanguage() {
        Locale locale = new Locale(PreferencesUtils.getLanguage());
        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.locale = locale;

        if (PreferencesUtils.getLanguage().equals("ar")) {
            configuration.setLayoutDirection(new Locale(PreferencesUtils.getLanguage()));
        } else {
            configuration.setLayoutDirection(Locale.getDefault());
        }

        this.getResources().updateConfiguration(configuration, this.getResources().getDisplayMetrics());

        PreferencesUtils.setLanguage(PreferencesUtils.getLanguage());
    }


}