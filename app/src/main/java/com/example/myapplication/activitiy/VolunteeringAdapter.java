package com.example.myapplication.activitiy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.model.objects.Volunteering;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class VolunteeringAdapter extends ArrayAdapter<Volunteering> {
    private Context mContext;
    private ArrayList<Volunteering> volunteeringArrayListList;

    public VolunteeringAdapter(Context context, ArrayList<Volunteering> volunteeringArrayListList) {
        super(context, R.layout.list_view, volunteeringArrayListList);
        this.mContext = context;
        this.volunteeringArrayListList = volunteeringArrayListList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Volunteering v = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_view,parent,false);
        }

        TextView name = convertView.findViewById(R.id.associationName);
        TextView title = convertView.findViewById(R.id.title);
        TextView city = convertView.findViewById(R.id.cityvol);
        TextView startTime = convertView.findViewById(R.id.lv_st);
        TextView endTime = convertView.findViewById(R.id.lv_et);
        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy  HH:mm");
        name.setText(v.getAssociation_name());
        title.setText(v.getTitle());
        city.setText(v.getLocation());
        startTime.setText(dt1.format(v.getStart()));
        endTime.setText(dt1.format(v.getEnd()));

        return convertView;
    }
}

