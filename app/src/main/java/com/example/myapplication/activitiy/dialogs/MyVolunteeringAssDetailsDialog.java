package com.example.myapplication.activitiy.dialogs;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.example.myapplication.activitiy.MyVolAssociationActivity;
import com.example.myapplication.model.MyVolAssModel;
import com.example.myapplication.model.objects.Volunteering;

public class MyVolunteeringAssDetailsDialog extends DialogFragment {
    Volunteering volunteering;
    MyVolAssociationActivity activity;
    MyVolAssModel model;

    public MyVolunteeringAssDetailsDialog(Volunteering volunteering, MyVolAssociationActivity activity,
                                          MyVolAssModel model){
        this.volunteering = volunteering;
        this.activity = activity;
        this.model = model;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myvolunteering_list_ass_dialog, container, true);
        RelativeLayout mail = view.findViewById(R.id.rlmail);
        RelativeLayout sms = view.findViewById(R.id.rlsms);
        RelativeLayout delete = view.findViewById(R.id.rldelete);
        RelativeLayout edit = view.findViewById(R.id.rledit);
        RelativeLayout volunteers = view.findViewById(R.id.rllistvolunteers);

        edit.setOnClickListener(v -> {
            Intent intent = activity.passData(volunteering.getUid(),volunteering.getTitle(),
                    volunteering.getLocation(),volunteering.getNum_vol(),volunteering.getStart(),
                    volunteering.getEnd(),volunteering.getPhone());
            startActivity(intent);
            dismiss();
        });

        delete.setOnClickListener(v -> {
            model.removeVolunteering(volunteering);
            dismiss();
        });

        sms.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]
                                {Manifest.permission.SEND_SMS}, 100);
            }
            else{
                model.sendSMSToVolunteers(volunteering);
                dismiss();
            }
        });

        mail.setOnClickListener(v -> {
            model.sendMails(volunteering);
            dismiss();
        });

        return view;
    }

}
