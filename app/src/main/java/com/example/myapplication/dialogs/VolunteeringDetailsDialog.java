package com.example.myapplication.dialogs;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.model.VolunteeringListModel;
import com.example.myapplication.R;
import com.example.myapplication.VolunteeringListActivity;
import com.example.myapplication.objects.Volunteering;

import java.text.SimpleDateFormat;

public class VolunteeringDetailsDialog extends DialogFragment {
    Volunteering volunteering;
    VolunteeringListActivity activity;
    VolunteeringListModel model;

    public VolunteeringDetailsDialog(Volunteering v, VolunteeringListActivity activity, VolunteeringListModel model){
        super();
        this.volunteering = v;
        this.activity = activity;
        this.model = model;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.volunteering_detail_dialog, container, true);
        ImageButton call = view.findViewById(R.id.floatingActionButtonCall);
        ImageButton whatsapp = view.findViewById(R.id.floatingActionButtonWhatsapp);
        ImageButton cancel = view.findViewById(R.id.floatingActionButtonCancel);
        ImageButton add = view.findViewById(R.id.floatingActionButtonAdd);
        TextView title = view.findViewById(R.id.tt);
        TextView ass = view.findViewById(R.id.tt3);
        TextView location = view.findViewById(R.id.tt4);
        TextView date = view.findViewById(R.id.tt5);

        title.setText(volunteering.getTitle());
        ass.setText("עמותת " + volunteering.getAssociation_name());
        location.setText(volunteering.getLocation());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy  HH:mm");
        String s = "מתאריך: " + formatter.format(volunteering.getStart()) +"\n" + "עד תאריך: " + formatter.format(volunteering.getEnd());
        date.setText(s);

        call.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CALL_PHONE},100);
            }
            else {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:"+volunteering.getPhone()));
                startActivity(i);
            }
        });
        cancel.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v -> {
            model.addVolunteeringToVolunteer(volunteering);
            model.removeVolunteering(volunteering);
            activity.adapter.notifyDataSetChanged();
            dismiss();
        });
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean whatsapp_installed = appInstalled("com.whatsapp");
                if (whatsapp_installed){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    //todo : check
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+972"+ "585275275"));
                    startActivity(intent);
                }
                else
                    Toast.makeText(activity,"Whatsapp not installed on your device", Toast.LENGTH_LONG).show();
            }
        });



        return view;
    }
    private boolean appInstalled(String url){
        PackageManager pm = activity.getPackageManager();
        boolean installed;
        try {
            pm.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            installed = false;
        }
        return installed;
    }
}

