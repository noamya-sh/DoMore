package com.example.firebase.db;

import com.example.myapplication.objects.Association;
import com.example.myapplication.objects.Volunteering;
import com.google.firebase.firestore.CollectionReference;

public class VolunteeringDB extends FirebaseDB{
    CollectionReference mCollection = mDB.collection("volunteering");

    public String generateNewID(){
        return mCollection.document().getId();
    }
    public void addNewVolunteering(Volunteering volunteering){
        mCollection.document(volunteering.getUid()).set(volunteering);
    }
}
