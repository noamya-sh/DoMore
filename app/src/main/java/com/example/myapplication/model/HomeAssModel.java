package com.example.myapplication.model;
import com.example.myapplication.db.AssociationDB;
import com.example.myapplication.db.UserDB;
import com.example.myapplication.activitiy.HomeAssoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class HomeAssModel {
    AssociationDB adb = new AssociationDB();

    public void getName(OnCompleteListener<DocumentSnapshot> oc){
        adb.getName(oc);
    }
    public void signOut(){
        UserDB udb = new UserDB();
        udb.signOut();
    }

}
