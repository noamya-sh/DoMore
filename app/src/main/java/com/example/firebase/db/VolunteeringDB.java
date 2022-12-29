package com.example.firebase.db;

import com.example.myapplication.objects.Volunteering;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

public class VolunteeringDB extends FirebaseDB{
    CollectionReference mCollection = mDB.collection("volunteering");

    public String generateNewID(){
        return mCollection.document().getId();
    }
    public void setVolunteering(Volunteering volunteering){
        mCollection.document(volunteering.getUid()).set(volunteering);
    }
    public void getListVolunteering(OnCompleteListener<QuerySnapshot> oc){
        mCollection.whereGreaterThan("start",new Date()).get().addOnCompleteListener(oc);
    }

    public DocumentReference getDocumentReference(Volunteering volunteering) {
        return mCollection.document(volunteering.getUid());
    }
}
