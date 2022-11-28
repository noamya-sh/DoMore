package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register_association extends Activity {
    FirebaseFirestore db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_association);
        db = FirebaseFirestore.getInstance();
        Button b = findViewById(R.id.acc);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText associationName = (EditText)findViewById(R.id.editTextAsssociationName);
                EditText email = (EditText)findViewById(R.id.editTextTextEmailAddress);
                EditText password = (EditText)findViewById(R.id.editTextTextPassword);
                EditText phone = (EditText)findViewById(R.id.editTextPhone);
                Spinner category = (Spinner) findViewById(R.id.planets_spinner);
                Map<String, Object> m = new HashMap<>();
                m.put("name", associationName.getText().toString());
                m.put("email", email.getText().toString());
                m.put("phone",Integer.parseInt(phone.getText().toString()));
                m.put("password", password.getText().toString());
                m.put("category", category.getSelectedItem().toString());
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
//        Button b2 = findViewById(R.id.acc2);
//        b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                db.collection("associations").
//                whereEqualTo("category","חינוך")
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                        System.out.println(document.getData().get("email").toString());
////                                        Log.d(TAG, document.getId() + " => " + document.getData());
//                                    }
//                                } else {
//                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                        System.out.println(document.getData().get("email").toString());
////                                        Log.d(TAG, document.getId() + " => " + document.getData());
//                                    }
////                                    Log.d(TAG, "Error getting documents: ", task.getException());
//                                }
//                            }
//                        });
////                CollectionReference cr = db.collection("associations");
////                Query q = cr.whereEqualTo("category","חינוך");
////
////                System.out.println(q.toString());
//            }
//        });
    }
}
