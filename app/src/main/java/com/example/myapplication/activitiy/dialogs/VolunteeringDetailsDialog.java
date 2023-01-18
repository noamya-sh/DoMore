package com.example.myapplication.activitiy.dialogs;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.model.ListVolunteeringModel;
import com.example.myapplication.activitiy.MyVolVolunteerActivity;
import com.example.myapplication.R;
import com.example.myapplication.activitiy.VolunteeringListActivity;
import com.example.myapplication.model.objects.Volunteering;

import java.text.SimpleDateFormat;

//dialog for each volunteering in volunteering list that is available and open for registration
public class VolunteeringDetailsDialog extends DialogFragment {
    public enum Type{
        registered,candidate
    }
    Volunteering volunteering;
    Activity activity;
    ListVolunteeringModel model;
    Type type;

    public VolunteeringDetailsDialog(Volunteering v, Activity activity, ListVolunteeringModel model,Type type){
        super();
        this.volunteering = v;
        this.activity = activity;
        this.model = model;
        this.type = type;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.volunteering_detail_dialog, container, true);
        RelativeLayout call = view.findViewById(R.id.rlcall);
        RelativeLayout whatsapp = view.findViewById(R.id.rlwhatsapp);
        RelativeLayout cancel = view.findViewById(R.id.rlcancel);
        RelativeLayout add = view.findViewById(R.id.rladd);

        if (this.type == Type.candidate){
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(400, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            layoutParams.setMargins(0, 10, 0, 0);
            layoutParams.addRule(RelativeLayout.BELOW, whatsapp.getId());
            add.setLayoutParams(layoutParams);
            cancel.setVisibility(View.GONE);
            add.setOnClickListener(v -> {
                model.HandleMainChoose(volunteering);
                Toast.makeText(activity,"שובצת להתנדבות זו",Toast.LENGTH_SHORT).show();
                dismiss();
            });
        }
        else {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(400, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            layoutParams.setMargins(0, 10, 0, 0);
            layoutParams.addRule(RelativeLayout.BELOW, whatsapp.getId());
            cancel.setLayoutParams(layoutParams);
            add.setVisibility(View.GONE);
            cancel.setOnClickListener(v -> {
                model.HandleMainChoose(volunteering);
                Toast.makeText(activity,"בוטלה הרשמתך להתנדבות זו",Toast.LENGTH_SHORT).show();
                dismiss();
            });
        }
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

        whatsapp.setOnClickListener(v -> {
            boolean whatsapp_installed = appInstalled("com.whatsapp");
            if (whatsapp_installed){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                //todo : check
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+972"+ volunteering.getPhone().substring(1)));
                startActivity(intent);
            }
            else
                Toast.makeText(activity,"Whatsapp not installed on your device", Toast.LENGTH_LONG).show();
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

