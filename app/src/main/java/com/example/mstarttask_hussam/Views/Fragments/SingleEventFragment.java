package com.example.mstarttask_hussam.Views.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.mstarttask_hussam.Controller.EventsRoom.AppDatabase;
import com.example.mstarttask_hussam.Controller.Network.CallBack;
import com.example.mstarttask_hussam.Model.Beans.Events.Event;
import com.example.mstarttask_hussam.Model.Beans.converterResults.ConverterResults;
import com.example.mstarttask_hussam.Model.basic.MyApplication;
import com.example.mstarttask_hussam.Model.utilits.AppConstants;
import com.example.mstarttask_hussam.R;
import com.example.mstarttask_hussam.Views.Dialogs.ConfirmationDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class SingleEventFragment extends Fragment implements CallBack {

    private final String flag;
    private final int position;

    private EditText gregorianDate, hijriDate, eventTitle, eventDescription;
    private Button saveBtn, deleteBtn;

    private SimpleDateFormat dateFormat;
    private Calendar calendar;
    private AppDatabase appDatabase;

    private Dialog progressDialog;

    public SingleEventFragment(String flag, int position) {
        this.flag = flag;
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_event, container, false);

        initials(view);
        clicks();

        return view;
    }

    private void initials(View view) {
        saveBtn = view.findViewById(R.id.save_btn);
        deleteBtn = view.findViewById(R.id.delete_btn);

        gregorianDate = view.findViewById(R.id.gregorian_date_value);
        hijriDate = view.findViewById(R.id.hijri_date_value);
        eventTitle = view.findViewById(R.id.event_value);
        eventDescription = view.findViewById(R.id.event_description_value);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        calendar = Calendar.getInstance();

        progressDialog = new Dialog(getContext());
        progressDialog.setContentView(R.layout.dialog_progress);
        progressDialog.setCancelable(false);

        appDatabase = Room
                .databaseBuilder(getContext(), AppDatabase.class, "events_db")
                .allowMainThreadQueries()
                .build();

        if (flag.equalsIgnoreCase("add")){
            deleteBtn.setVisibility(View.GONE);
            gregorianDate.setText(dateFormat.format(calendar.getTime()));
            getHijriDate(dateFormat.format(calendar.getTime()));
        } else if (flag.equalsIgnoreCase("edit")){
            progressDialog.show();
            Event event = appDatabase.eventDao().getEventByPosition(position);
            eventTitle.setText(event.getEvent_name());
            eventDescription.setText(event.getEvent_description());
            gregorianDate.setText(event.getGregorian_date());
            hijriDate.setText(event.getHijri_date());
            deleteBtn.setVisibility(View.VISIBLE);
            progressDialog.dismiss();
        }
    }

    private void clicks() {

        gregorianDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(getContext(), (view1, year1, month1, dayOfMonth) -> {

                    calendar.set(Calendar.YEAR, year1);
                    calendar.set(Calendar.MONTH, month1);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    progressDialog.show();

                    gregorianDate.setText(dateFormat.format(calendar.getTime()));
                    getHijriDate(dateFormat.format(calendar.getTime()));
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEvent();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkFields()){
                    progressDialog.show();
                    if (flag.equalsIgnoreCase("add")){
                        insertEvent();
                    } else if (flag.equalsIgnoreCase("edit")){
                        updateEvent();
                    }
                }
            }
        });
    }

    private boolean checkFields() {

        boolean checked = true;

        String eventNameRegex = "^.{3,50}$";
        if (!eventTitle.getText().toString().matches(eventNameRegex)) {
            eventTitle.setError(getString(R.string.the_event_name_must_be_at_minimum_3_characters_maximum_50_characters));
            checked = false;
        }

        String eventDescriptionRegex = "^.{10,200}$";
        if (!eventDescription.getText().toString().matches(eventDescriptionRegex)){
            eventDescription.setError(getString(R.string.the_event_description_must_be_at_minimum_10_characters_maximum_200_characters));
            checked = false;
        }

        String dateRegex = "^(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[0-2])-(\\d{4})$";
        if (!gregorianDate.getText().toString().matches(dateRegex)) {
            gregorianDate.setError(getString(R.string.invalid_date));
            checked = false;
        }

        if (!hijriDate.getText().toString().matches(dateRegex)) {
            hijriDate.setError(getString(R.string.invalid_date));
            checked = false;
        }

        return checked;
    }

    private void insertEvent(){
        Event event = new Event();
        event.setEvent_name(eventTitle.getText().toString());
        event.setEvent_description(eventDescription.getText().toString());
        event.setGregorian_date(gregorianDate.getText().toString());
        event.setHijri_date(hijriDate.getText().toString());
        event.setServer_datetime(getCurrentDateTimeFormatted());

        appDatabase.eventDao().insert(event);
        progressDialog.dismiss();
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private void deleteEvent() {
        new ConfirmationDialog(getContext(), "deleteEvent", position).show();
    }

    private void updateEvent(){
        Event eventToUpdate = appDatabase.eventDao().getEventByPosition(position);

        if (eventToUpdate != null) {
            // Modify the event data
            eventToUpdate.setEvent_name(eventTitle.getText().toString());
            eventToUpdate.setEvent_description(eventDescription.getText().toString());
            eventToUpdate.setGregorian_date(gregorianDate.getText().toString());
            eventToUpdate.setHijri_date(hijriDate.getText().toString());
            eventToUpdate.setServer_datetime(getCurrentDateTimeFormatted());

            appDatabase.eventDao().update(eventToUpdate);
        }

        progressDialog.dismiss();
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private void getHijriDate(String gregorianDate) {

        HashMap<String ,Object> params = new HashMap<>();
        params.put("date", gregorianDate);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.From_G_To_H_URL, AppConstants.From_G_To_H_TAG, ConverterResults.class, params);
    }

    public static String getCurrentDateTimeFormatted() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss", Locale.ENGLISH);
        return dateFormat.format(calendar.getTime());
    }

    @Override
    public void onSuccess(int tag, boolean isSuccess, Object result) {

        progressDialog.dismiss();

        if (isSuccess) {
            ConverterResults converterResults = (ConverterResults) result;
            hijriDate.setText(converterResults.getData().getHijri().getDate());
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