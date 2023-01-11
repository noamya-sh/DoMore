package com.example.myapplication.db;
import com.example.myapplication.model.MainModel;
import com.example.myapplication.model.apiserver.RegisterAPI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void updatePassword(String password) {
        mAuth.getCurrentUser().updatePassword(password);
    }


    //***** nodejs *****//
    public void CheckUserTypeServer(Callback<ResponseBody> function){
        Call<ResponseBody> call = RegisterAPI.getInstance().getAPI().DocumentUID(getUID());
        call.enqueue(function);
    }
    // for json response from node-js server
    public static class VolunteerType {
        Boolean type;
        public VolunteerType(){}
        public VolunteerType(Boolean type){ this.type = type; }
        public Boolean getType() {
            return type;
        }
        public void setType(Boolean type) {
            this.type = type;
        }
    }
    //***** nodejs *****//


}
