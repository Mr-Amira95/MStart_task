package com.example.mstarttask_hussam.Model.basic;



import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class BasicActivity extends AppCompatActivity {
    public BasicActivity activity;
    public ProgressDialog dialog;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = this;
        dialog = ProgressDialog.show(activity, "",
                "Loading. Please wait...", true);
        dialog.setCancelable(true);
        dialog.dismiss();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
