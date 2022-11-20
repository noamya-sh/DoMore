package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class add_association extends Activity {
    FirebaseFirestore db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_association);
        db = FirebaseFirestore.getInstance();
        Button b = findViewById(R.id.acc);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText txtDescription = (EditText)findViewById(R.id.editTextTextPersonName);
                EditText txtDescription3 = (EditText)findViewById(R.id.editTextTextEmailAddress);
                Spinner txtDescription2 = (Spinner) findViewById(R.id.planets_spinner);
                Map<String, Object> m = new HashMap<>();
                m.put("name",txtDescription.getText().toString());
                m.put("email",txtDescription3.getText().toString());
                m.put("category",txtDescription2.getSelectedItem().toString());
                db.collection("associations").add(m).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        System.out.println("success");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("failure");
                    }
                });

            }
        });
        Button b2 = findViewById(R.id.acc2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("associations").
                whereEqualTo("category","חינוך")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        System.out.println(document.getData().get("email").toString());
//                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                    }
                                } else {
//                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
//                CollectionReference cr = db.collection("associations");
//                Query q = cr.whereEqualTo("category","חינוך");
//
//                System.out.println(q.toString());
            }
        });
    }
}
