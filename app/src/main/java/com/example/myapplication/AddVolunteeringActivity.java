package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.dialogs.SearchDialog;
import com.example.myapplication.objects.Association;
import com.example.myapplication.objects.Volunteering;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.Stack;

public class AddVolunteeringActivity extends AppCompatActivity {
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    Association association;
    DocumentReference asso_ref;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vol);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        this.asso_ref = db.collection("associations").document(user.getUid());
        this.asso_ref.get().addOnSuccessListener(ds -> {
            this.association = ds.toObject(Association.class);
        });
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
        publ.setOnClickListener(v -> {
            DocumentReference uid = db.collection("volunteering").document();
            Volunteering volunteering = new Volunteering(uid.getId(),association.getName(),
                    vol_details.getText().toString(),location.getSelectedItem().toString(),
                    association.getCategory(), Integer.parseInt(phone.getText().toString()),
                    sts1.pop().toDate(),sts2.pop().toDate(),asso_ref,
                    Integer.parseInt(capacity.getText().toString()),
                    Integer.parseInt(capacity.getText().toString()));
            volunteering.addNewVolunteering(AddVolunteeringActivity.this);
            association.addVolunteering(volunteering);
        });
    }
}
