package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class listAdapterVolunteering extends ArrayAdapter<volunteering> {
    private Context mContext;
    private int mResource;

    public listAdapterVolunteering(Context context,int resource,ArrayList<volunteering> volArrayList) {
        super(context,resource ,volArrayList);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        volunteering v = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_view,parent,false);
        }

        TextView name = convertView.findViewById(R.id.associationName);
        TextView title = convertView.findViewById(R.id.title);
        TextView city = convertView.findViewById(R.id.cityvol);
//        TextView startdate = convertView.findViewById(R.id.date);
        TextView startTime = convertView.findViewById(R.id.strtTime);
        TextView endTime = convertView.findViewById(R.id.endTime);
        SimpleDateFormat dt1 = new SimpleDateFormat("hh:mm");
        name.setText(v.association);
        title.setText(v.title);
        city.setText(v.location);
        startTime.setText(dt1.format(v.start_date));
        endTime.setText(dt1.format(v.end_date));

        return convertView;//super.getView(position, convertView, parent);
    }

}
