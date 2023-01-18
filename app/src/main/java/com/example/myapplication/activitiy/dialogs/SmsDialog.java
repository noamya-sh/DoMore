package com.example.myapplication.activitiy.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.example.myapplication.activitiy.MyVolAssociationActivity;
import com.example.myapplication.model.MyVolAssModel;
import com.example.myapplication.model.objects.Volunteering;

//dialog for write and send sms to all registered volunteers
public class SmsDialog extends DialogFragment {
    MyVolAssociationActivity activity;
    MyVolAssModel model;
    Volunteering volunteering;

    public SmsDialog(MyVolAssociationActivity activity, MyVolAssModel model, Volunteering volunteering){
        this.activity = activity;
        this.model = model;
        this.volunteering = volunteering;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sms_dialog, container, true);
        EditText content = view.findViewById(R.id.smscontent);
        Button cancel = view.findViewById(R.id.smscancel);
        Button clear = view.findViewById(R.id.smsclear);
        Button send = view.findViewById(R.id.smssend);

        cancel.setOnClickListener(v -> dismiss());
        clear.setOnClickListener(v -> content.getText().clear());
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.sendSMSToVolunteers(volunteering,content.getText().toString());
                dismiss();
            }
        });
        return view;
    }
}
