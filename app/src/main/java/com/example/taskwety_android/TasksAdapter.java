package com.example.taskwety_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TasksAdapter extends ArrayAdapter<Task> {
    public TasksAdapter(Context context, ArrayList<Task> Tasks) {
        super(context, 0, Tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Get the data item for this position
        Task Task = getItem(position);
        // Lookup view for data population
        TextView id = (TextView) convertView.findViewById(R.id.taskIdTextView);
        TextView title = (TextView) convertView.findViewById(R.id.taskTitleTextView);
        TextView description = (TextView) convertView.findViewById(R.id.taskDescriptionTextView);

        // Populate the data into the template view using the data object
        assert Task != null;
        id.setText(String.valueOf(Task.getID()));
        title.setText(Task.getTitle());
        description.setText(Task.getDescription());

        // Return the completed view to render on screen
        return convertView;
    }
}
