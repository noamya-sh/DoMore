package com.example.firebase.db;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentSnapshot;

public class UserDB extends FirebaseDB{
    public void login(String email, String pass, OnCompleteListener<AuthResult> oc){
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(oc);
    }
    public void signOut(){
        mAuth.signOut();
    }
    public void CheckUserType(OnCompleteListener<DocumentSnapshot> oc) {
        mDB.collection("volunteers").document(mAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(oc);
    }
    public boolean isConnect(){
        return mAuth.getCurrentUser() != null;
    }
    public void registerNewUser(String email,String pass, OnCompleteListener<AuthResult> oc){
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(oc);
    }
    public String getUID(){
        return mAuth.getCurrentUser().getUid();
    }
}
