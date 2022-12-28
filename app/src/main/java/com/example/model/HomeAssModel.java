package com.example.model;
import com.example.firebase.db.AssociationDB;
import com.example.firebase.db.UserDB;
import com.example.myapplication.HomeAssoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class HomeAssModel {
    AssociationDB adb = new AssociationDB();
    HomeAssoActivity activity;

    public HomeAssModel(HomeAssoActivity activity){
        this.activity = activity;
    }

    public void getName(OnCompleteListener<DocumentSnapshot> oc){
        adb.getName(oc);
    }
    public void signOut(){
        UserDB udb = new UserDB();
        udb.signOut();
    }

}
