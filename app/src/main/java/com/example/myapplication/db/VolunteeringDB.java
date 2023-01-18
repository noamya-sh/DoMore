package com.example.myapplication.db;

import com.example.myapplication.model.objects.Volunteering;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.List;

//for get, set and update data form volunteering collection in firestore
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
    public void getMyVolunteering(List<String> ids, OnSuccessListener<QuerySnapshot> os){
        mCollection.whereIn("uid",ids).get().addOnSuccessListener(os);
    }

    public void removeVolunteering(Volunteering v) {
        mCollection.document(v.getUid()).delete();
    }

    public void getVolunteering(String id,OnSuccessListener<DocumentSnapshot> os) {
        mCollection.document(id).get().addOnSuccessListener(os);
    }
}
