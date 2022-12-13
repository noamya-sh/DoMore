package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class AddvolunteeringActivity extends AppCompatActivity {
    FirebaseFirestore db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vol);
        db = FirebaseFirestore.getInstance();
        Button publ = (Button) findViewById(R.id.publish_vol);
        publ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                EditText vol_details = (EditText)findViewById(R.id.volunteering_details);
                Spinner location = (Spinner) findViewById(R.id.cities_spinner_for_publish);
                EditText date = (EditText)findViewById(R.id.date);
                EditText start_hour = (EditText)findViewById(R.id.start_hour);
                EditText end_hour = (EditText)findViewById(R.id.end_hour);
                EditText phone = (EditText) findViewById(R.id.contact_Phone);
                EditText capacity = (EditText) findViewById(R.id.Num_vol_required);

//                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
//                String date2 = df.format(date.getText().toString());

                Map<String, Object> m = new HashMap<>();
//                    m.put("uid",user.getUid());
                m.put("vol_details",vol_details.getText().toString());
                m.put("location", location.getSelectedItem().toString());
                m.put("date", date.getText().toString());
                m.put("start_hour", start_hour.getText().toString());
                m.put("end_hour",end_hour.getText().toString());
                m.put("phone",Integer.parseInt(phone.getText().toString()));
                m.put("capacity",capacity.getText().toString());
                db.collection("volunteering").add(m);
            }
        });

    }






}
