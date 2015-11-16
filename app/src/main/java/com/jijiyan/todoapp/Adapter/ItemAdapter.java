package com.jijiyan.todoapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jijiyan.todoapp.R;
import com.jijiyan.todoapp.model.Item;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {
    public ItemAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Item item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.items, parent, false);
        }
        // Lookup view for data population
        TextView tvItemName = (TextView) convertView.findViewById(R.id.tvItemName);
        TextView tvItemNote = (TextView) convertView.findViewById(R.id.tvItemNote);
        // Populate the data into the template view using the data object
        tvItemName.setText(item.name);
        tvItemNote.setText(item.note);
        // Return the completed view to render on screen
        return convertView;
    }

}
