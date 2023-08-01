package com.example.mstarttask_hussam.Views.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.mstarttask_hussam.Controller.Adapters.EventsAdapter;
import com.example.mstarttask_hussam.Controller.EventsRoom.AppDatabase;
import com.example.mstarttask_hussam.Model.Beans.Events.Event;
import com.example.mstarttask_hussam.R;
import com.example.mstarttask_hussam.Views.Activities.MainActivity;

import java.util.List;

public class EventsFragment extends Fragment {

    private RecyclerView eventsRecyclerview;
    private Button addEvent;
    private ImageView noResults;
    private Dialog progressDialog;

    public EventsFragment() {

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
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        initials(view);
        clicks();

        return view;
    }

    private void initials(View view) {
        noResults = view.findViewById(R.id.no_results);
        addEvent = view.findViewById(R.id.add_new);
        eventsRecyclerview = view.findViewById(R.id.events_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        eventsRecyclerview.setLayoutManager(linearLayoutManager);

        progressDialog = new Dialog(getContext());
        progressDialog.setContentView(R.layout.dialog_progress);
        progressDialog.setCancelable(false);

        progressDialog.show();

        AppDatabase appDatabase = Room
                .databaseBuilder(getContext(), AppDatabase.class, "events_db")
                .allowMainThreadQueries()
                .build();

        List<Event> events = appDatabase.eventDao().getAllEvents();
        setEvents(events);

    }

    private void clicks() {
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SingleEventFragment("Add", -1));
            }
        });
    }

    private void setEvents(List<Event> events) {
        if (events.size() > 0) {
            noResults.setVisibility(View.GONE);
            eventsRecyclerview.setVisibility(View.VISIBLE);
            EventsAdapter eventsAdapter = new EventsAdapter(getContext(), events);
            eventsRecyclerview.setAdapter(eventsAdapter);
        } else {
            noResults.setVisibility(View.VISIBLE);
            eventsRecyclerview.setVisibility(View.GONE);
        }

        progressDialog.dismiss();
    }

    private void setFragment(Fragment fragment){
        FragmentManager fragmentManager = ((MainActivity) getContext()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}