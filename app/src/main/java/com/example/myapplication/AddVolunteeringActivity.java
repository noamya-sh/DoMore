package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.model.AddVolunteeringModel;
import com.example.myapplication.dialogs.SearchDialog;
import com.google.firebase.Timestamp;
import java.util.Date;
import java.util.Stack;

public class AddVolunteeringActivity extends AppCompatActivity {
    private AddVolunteeringModel model;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vol);
        model = new AddVolunteeringModel(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        addVol();
    }

    private void addVol() {
        Button publ = findViewById(R.id.publish_vol);
        EditText vol_details = findViewById(R.id.volunteering_details);
        Spinner location = findViewById(R.id.cities_spinner_for_publish);
        Button start = findViewById(R.id.addvol_from);
        Button end = findViewById(R.id.addvol_un);
        TextView from = findViewById(R.id.addvol_start);
        TextView un = findViewById(R.id.addvol_end);
        Timestamp now = new Timestamp(new Date());
        Stack<Timestamp> sts1 = new Stack<>();
        sts1.push(now);
        Stack<Timestamp> sts2 = new Stack<>();
        sts2.push(now);
        SearchDialog.getDateTime(start, from, sts1);
        SearchDialog.getDateTime(end, un, sts2);
        EditText phone = findViewById(R.id.contact_Phone);
        EditText capacity = findViewById(R.id.Num_vol_required);
        publ.setOnClickListener(v -> model.addNewVol(vol_details.getText().toString(),location.getSelectedItem().toString(),
                Integer.parseInt(phone.getText().toString()),
                sts1.pop().toDate(),sts2.pop().toDate(), Integer.parseInt(capacity.getText().toString())));
    }

    public void goToHome() {
        startActivity(new Intent(AddVolunteeringActivity.this, HomeAssoActivity.class));
    }
}
