package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class listAdapterVolunteering extends ArrayAdapter<volunteering> {

    public listAdapterVolunteering(Context context, ArrayList<volunteering> volArrayList) {
        super(context,R.layout.list_view ,volArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        volunteering v = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view,parent,false);
        }

        TextView name = convertView.findViewById(R.id.associationName);
        TextView title = convertView.findViewById(R.id.title);
        TextView city = convertView.findViewById(R.id.cityvol);
//        TextView startdate = convertView.findViewById(R.id.date);
        TextView startTime = convertView.findViewById(R.id.strtTime);
        TextView endTime = convertView.findViewById(R.id.endTime);

        name.setText(v.association);
        title.setText(v.title);
        city.setText(v.location);
        startTime.setText(v.start_date.toString());
        endTime.setText(v.end_date.toString());

        return super.getView(position, convertView, parent);
    }

}
