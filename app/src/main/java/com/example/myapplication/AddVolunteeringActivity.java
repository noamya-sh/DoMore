package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class AddVolunteeringActivity extends AppCompatActivity {
    FirebaseFirestore db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vol);
        addVol();

    }
    private void addVol() {
        db = FirebaseFirestore.getInstance();
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
        publ.setOnClickListener(v -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Map<String, Object> m = new HashMap<>();
            DocumentReference dr = db.collection("associations").document(user.getUid());

            dr.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
//                    String uid, String association_name, String title, String location,
//                            String category, String phone, Date start, Date end,
//                            DocumentReference association, int num_vol, int num_vol_left

                    DocumentSnapshot ds = task.getResult();
                    Association a = ds.toObject(Association.class);
                    DocumentReference uid = db.collection("volunteering").document();

                    Volunteering volunteering = new Volunteering(uid.getId(),a.getName(),
                    vol_details.getText().toString(),location.getSelectedItem().toString(),
                            ds.getString("category"), Integer.parseInt(phone.getText().toString()),
                            sts1.pop().toDate(),sts2.pop().toDate(),dr,
                            Integer.parseInt(capacity.getText().toString()),
                            Integer.parseInt(capacity.getText().toString()));
                    volunteering.updateFirestore(AddVolunteeringActivity.this);
                    a.addVolunteering(volunteering);

//                    m.put("association", dr);
//                    m.put("association_name",ds.getString("name"));
//                    m.put("title", vol_details.getText().toString());
//                    m.put("location", location.getSelectedItem().toString());
//                    m.put("start", sts1.pop());
//                    m.put("end", sts2.pop());
//                    m.put("phone", phone.getText().toString());
//                    m.put("num_vol", Integer.parseInt(capacity.getText().toString()));
//                    m.put("num_vol_left", Integer.parseInt(capacity.getText().toString()));
//                    db.collection("volunteering").add(volunteering).addOnCompleteListener(task2 -> {
//                        Volunteering.updateVolunteeringWithServer(volunteering,task2.getResult().getId());
//                        startActivity(new Intent(AddVolunteeringActivity.this, asso_home.class));
//                    });
                }
            });
        });
    }
}
