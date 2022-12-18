package com.example.myapplication.dialogs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.RegisterAssociationActivity;
import com.example.myapplication.RegisterVolunteerActivity;
import com.example.myapplication.R;

public class BeforeRegisterDialog extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_before_reg, container, true);
        Button vol = view.findViewById(R.id.dbr_vol);
        Button asso = view.findViewById(R.id.dbr_ass);
        Context c = getContext();

        vol.setOnClickListener(v -> {
            startActivity(new Intent(c, RegisterVolunteerActivity.class));
            dismiss();
        });
        asso.setOnClickListener(v -> {
            startActivity(new Intent(c, RegisterAssociationActivity.class));
            dismiss();
        });
        return view;
    }
}
