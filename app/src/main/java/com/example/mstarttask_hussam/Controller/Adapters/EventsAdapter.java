package com.example.mstarttask_hussam.Controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mstarttask_hussam.Model.Beans.Events.Event;
import com.example.mstarttask_hussam.R;
import com.example.mstarttask_hussam.Views.Activities.MainActivity;
import com.example.mstarttask_hussam.Views.Dialogs.ConfirmationDialog;
import com.example.mstarttask_hussam.Views.Fragments.SingleEventFragment;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter <EventsAdapter.ViewHolder>{

    Context context;
    List<Event> data;

    public EventsAdapter(Context context, List<Event> data) {
        this.context = context;
        this.data= data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_event, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Event current = data.get(position);

        holder.eventTitle.setText(current.getEvent_name());
        holder.eventDescription.setText(current.getEvent_description());
        holder.gregorianDate.setText(current.getGregorian_date());
        holder.hijriDate.setText(current.getHijri_date());

        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ConfirmationDialog(context, "deleteEvent", holder.getAdapterPosition()).show();
            }
        });

        holder.editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SingleEventFragment("edit", holder.getAdapterPosition()));
            }
        });

    }

    private void setFragment (Fragment fragment) {
        FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView eventTitle, eventDescription, gregorianDate, hijriDate;
        ImageView deleteIcon, editIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eventTitle = itemView.findViewById(R.id.title_value);
            eventDescription = itemView.findViewById(R.id.description_value);
            gregorianDate = itemView.findViewById(R.id.gregorian_value);
            hijriDate = itemView.findViewById(R.id.hijri_value);
            deleteIcon = itemView.findViewById(R.id.delete_icon);
            editIcon = itemView.findViewById(R.id.edit_icon);

        }
    }
}
