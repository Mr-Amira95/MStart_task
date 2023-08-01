package com.example.mstarttask_hussam.Views.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.example.mstarttask_hussam.Controller.EventsRoom.AppDatabase;
import com.example.mstarttask_hussam.Model.Beans.Events.Event;
import com.example.mstarttask_hussam.R;
import com.example.mstarttask_hussam.Views.Activities.MainActivity;
import com.example.mstarttask_hussam.Views.Fragments.EventsFragment;

public class ConfirmationDialog extends Dialog {

    private final Context context;
    private final String flag;
    private final int position;
    private Button cancel, confirm;
    private Dialog progressDialog;

    public ConfirmationDialog(@NonNull Context context, String flag, int position) {
        super(context);
        this.context = context;
        this.position = position;
        this.flag = flag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirmation);

        initials();
        clicks();

    }

    private void initials() {
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.setCancelable(true);

        progressDialog = new Dialog(getContext());
        progressDialog.setContentView(R.layout.dialog_progress);
        progressDialog.setCancelable(false);

        cancel = findViewById(R.id.cancel_button);
        confirm = findViewById(R.id.confirm_button);
    }

    private void clicks() {

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag.equalsIgnoreCase("exit")){
                    System.exit(0);
                } else if (flag.equalsIgnoreCase("deleteEvent")){
                    progressDialog.show();
                    deleteEvent();
                }
            }
        });
    }

    private void deleteEvent() {
       AppDatabase appDatabase = Room
                .databaseBuilder(getContext(), AppDatabase.class, "events_db")
                .allowMainThreadQueries()
                .build();

        Event eventToDelete = appDatabase.eventDao().getEventByPosition(position); // Get the second event for demonstration
        appDatabase.eventDao().delete(eventToDelete);
        progressDialog.dismiss();
        dismiss();
        setFragmentWithoutBack(new EventsFragment());
    }

    private void setFragmentWithoutBack(Fragment fragment){
        FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

}
