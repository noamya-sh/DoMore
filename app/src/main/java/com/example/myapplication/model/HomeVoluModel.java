package com.example.myapplication.model;

import com.example.myapplication.db.UserDB;
import com.example.myapplication.db.VolunteerDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class HomeVoluModel {
    VolunteerDB vdb = new VolunteerDB();

    public void getName(OnCompleteListener<DocumentSnapshot> oc){
        vdb.getName(oc);
    }
    public void signOut(){
        UserDB udb = new UserDB();
        udb.signOut();
    }
}
