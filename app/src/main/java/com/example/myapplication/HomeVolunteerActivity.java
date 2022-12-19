package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.nocomplete.MyVolAssociation;
import com.example.myapplication.nocomplete.MyVolVolunteer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeVolunteerActivity extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vol_home);
        db = FirebaseFirestore.getInstance();
        TextView wolcome = findViewById(R.id.volunteerName);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        DocumentReference docRef = db.collection("volunteers").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                       String k = "ברוך הבא " + document.getString("name");
                       wolcome.setText(k);
                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                }
            }
        });
        //get list of volunteering
        Button srch = findViewById(R.id.searvo);
        srch.setOnClickListener(v ->
                startActivity(new Intent(HomeVolunteerActivity.this, VolunteeringListActivity.class)));
        Button my_vol = findViewById(R.id.vh_myvol);
        my_vol.setOnClickListener(v -> startActivity(new Intent(HomeVolunteerActivity.this, MyVolVolunteer.class)));
        ImageButton ib = findViewById(R.id.volhome_logout);
        //log out
        Users.logOut(ib, HomeVolunteerActivity.this,this);
    }
}
