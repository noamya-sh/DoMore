package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class second_activity extends AppCompatActivity {
//    ActivityMainBinding bind;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        bind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.second_activity);
        db = FirebaseFirestore.getInstance();
        Date d = new Date();

        ArrayList<volunteering> arr = new ArrayList<>();

        db.collection("volunteering").whereGreaterThan("start",d).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()){
                            DocumentReference f = document.getDocumentReference("association");
                        DocumentReference dr = FirebaseFirestore.getInstance().document(f.getPath());
                        String association = get_association(dr);
                        volunteering v = new volunteering(
                                association,
                                document.getString("title"),
                                document.getString("location"),
                                Objects.requireNonNull(document.getTimestamp("start")).toDate(),
                                Objects.requireNonNull(document.getTimestamp("end")).toDate(),
                                Objects.requireNonNull(document.getLong("num_vol")).intValue(),
                                Objects.requireNonNull(document.getLong("num_vol_left")).intValue());
                        arr.add(v);
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }}
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }

            private String get_association(DocumentReference dr) {
                Task<DocumentSnapshot> task = dr.get();
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        return document.getString("name");
                    }
                }
                return null;
            }
        });

//        ImageView img = findViewById(R.id.imageView2);
//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(second_activity.this, MainActivity.class));
//            }
//        });
    }

}
