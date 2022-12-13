package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class AddvolunteeringActivity extends AppCompatActivity {
    FirebaseFirestore db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vol);
        db = FirebaseFirestore.getInstance();
        Button publ = (Button) findViewById(R.id.publish_vol);
        EditText vol_details = (EditText)findViewById(R.id.volunteering_details);
        Spinner location = (Spinner) findViewById(R.id.cities_spinner_for_publish);
        Button start = findViewById(R.id.addvol_from);
        Button end = findViewById(R.id.addvol_un);
        TextView from = findViewById(R.id.addvol_start);
        TextView un = findViewById(R.id.addvol_end);
        Timestamp now = new Timestamp(new Date());
        Stack<Timestamp> sts1 = new Stack<>();
        sts1.push(now);
        Stack<Timestamp> sts2 = new Stack<>();
        sts2.push(now);
        SearchDialog.getDateTime(start,from,sts1);
        SearchDialog.getDateTime(end,un,sts2);
        EditText phone = (EditText) findViewById(R.id.contact_Phone);
        NumberPicker capacity = findViewById(R.id.addvol_number_picker);
        publ.setOnClickListener(v -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Map<String, Object> m = new HashMap<>();
            DocumentReference dr = db.collection("associations").document(user.getUid());
            m.put("association",dr);
            m.put("title",vol_details.getText().toString());
            m.put("location", location.getSelectedItem().toString());
            m.put("start", sts1.pop());
            m.put("end",sts2.pop());
            m.put("phone",phone.getText().toString());
            m.put("num_vol",capacity.getValue());
            m.put("num_vol_left",capacity.getValue());
            db.collection("volunteering").add(m).addOnCompleteListener(task ->
                    startActivity(new Intent(AddvolunteeringActivity.this,asso_home.class)));
        });
    }
}
