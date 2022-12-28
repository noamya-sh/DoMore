package com.example.model;

import com.example.firebase.db.UserDB;
import com.example.firebase.db.VolunteerDB;
import com.example.myapplication.HomeVolunteerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class HomeVoluModel {
    VolunteerDB vdb = new VolunteerDB();
    HomeVolunteerActivity activity;

    public HomeVoluModel(HomeVolunteerActivity activity){
        this.activity = activity;
    }

    public void getName(OnCompleteListener<DocumentSnapshot> oc){
        vdb.getName(oc);
    }
    public void signOut(){
        UserDB udb = new UserDB();
        udb.signOut();
    }
}
