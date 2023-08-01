package com.example.mstarttask_hussam.Views.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mstarttask_hussam.Controller.Network.CallBack;
import com.example.mstarttask_hussam.Model.Beans.converterResults.ConverterResults;
import com.example.mstarttask_hussam.Model.basic.MyApplication;
import com.example.mstarttask_hussam.Model.utilits.AppConstants;
import com.example.mstarttask_hussam.R;
import com.example.mstarttask_hussam.Views.Activities.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class MainFragment extends Fragment implements CallBack {

    private EditText gregorianDateET;
    private Button convertBtn, eventsBtn;
    private TextView hijriTitleTV, hijriValueTV;

    private SimpleDateFormat dateFormat;
    private Calendar calendar;
    private Dialog progressDialog;

    private final String dateRegex = "^(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[0-2])-(\\d{4})$";

    public MainFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initials(view);
        clicks();

        return view;
    }

    private void initials(View view) {
        gregorianDateET = view.findViewById(R.id.gregorian_date);
        convertBtn = view.findViewById(R.id.convert_btn);
        eventsBtn = view.findViewById(R.id.events_btn);
        hijriTitleTV = view.findViewById(R.id.hijri_title);
        hijriValueTV = view.findViewById(R.id.hijri_value);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        calendar = Calendar.getInstance();
        gregorianDateET.setText(dateFormat.format(calendar.getTime()));

        progressDialog = new Dialog(getContext());
        progressDialog.setContentView(R.layout.dialog_progress);
        progressDialog.setCancelable(false);

    }

    private void clicks() {

        gregorianDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(getContext(), (view1, year1, month1, dayOfMonth) -> {

                    calendar.set(Calendar.YEAR, year1);
                    calendar.set(Calendar.MONTH, month1);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    gregorianDateET.setText(dateFormat.format(calendar.getTime()));
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gregorianDateET.getText().toString().matches(dateRegex)){
                    progressDialog.show();
                    convertToHijri();
                } else {
                    Toast.makeText(getContext(), R.string.invalid_date, Toast.LENGTH_SHORT).show();
                }
            }
        });

        eventsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new EventsFragment());
            }
        });

    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment).addToBackStack(null).commit();
    }

    private void convertToHijri() {

        HashMap<String ,Object> params = new HashMap<>();
        params.put("date", gregorianDateET.getText().toString());

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.From_G_To_H_URL, AppConstants.From_G_To_H_TAG, ConverterResults.class, params);

    }

    @Override
    public void onSuccess(int tag, boolean isSuccess, Object result) {

        progressDialog.dismiss();

        if (isSuccess){

            ConverterResults converterResults = (ConverterResults) result;
            hijriTitleTV.setVisibility(View.VISIBLE);
            hijriValueTV.setText(converterResults.getData().getHijri().getDate());

        } else {
            Toast.makeText(getContext(), getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFailure(int tag, Object result) {
        progressDialog.dismiss();
        Toast.makeText(getContext(), getString(R.string.check_your_internet_connection), Toast.LENGTH_SHORT).show();
    }
}