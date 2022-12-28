package com.example.firebase.db;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseDB {
    protected FirebaseFirestore mDB;
    protected FirebaseAuth mAuth;

    public FirebaseDB(){
        mDB = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }
}
