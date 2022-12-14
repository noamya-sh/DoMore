package com.example.myapplication;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

public class Association {
    String ID,name,email,password,category;
    int phone;
    DocumentReference ref;
    HashMap<String,DocumentReference> volunteering = new HashMap<>();

    public Association(String ID, String name, String email, String password, String category, int phone, DocumentReference ref) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.category = category;
        this.phone = phone;
        this.ref = ref;

    }
    public Association(DocumentReference dr){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        dr.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                DocumentSnapshot ds = task.getResult();
                this.ID = ds.getId();
                this.category = ds.getString("category");
                this.email = ds.getString("email");
                this.password = ds.getString("category");
                this.name = ds.getString("category");
                this.phone = Objects.requireNonNull(ds.getDouble("phone")).intValue();
                this.ref = dr;
                this.volunteering = new HashMap<>();
                dr.collection("my_volunteering").get().addOnCompleteListener(task1 -> {
                    if(task1.isSuccessful())
                        for(QueryDocumentSnapshot doc: task1.getResult())
                            if (doc.exists())
                                volunteering.put(doc.getId(), doc.getDocumentReference("refernce"));
                });
            }
        });
    }

}
