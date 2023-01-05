package com.example.myapplication.db;
import com.example.myapplication.model.objects.Volunteer;
import com.example.myapplication.model.objects.Volunteering;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VolunteerDB extends FirebaseDB{
    CollectionReference mCollection = mDB.collection("volunteers");

    public void getName(OnCompleteListener<DocumentSnapshot> oc){
        mCollection.document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(oc);
    }

    public void setVolunteer(Volunteer volunteer) {
        mCollection.document(volunteer.getUid()).set(volunteer);
    }
    public void getVolunteer(OnSuccessListener<DocumentSnapshot> os) {
        mCollection.document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(os);
    }

    public DocumentReference getDocumentReference(Volunteer volunteer) {
        return mCollection.document(volunteer.getUid());
    }
    public void removeVolunteeringFromAllVolunteers(Volunteering v) {
        List<String> ids = new ArrayList<>(v.getSignUpForVolunteering().keySet());
        mCollection.whereIn("uid",ids).get().addOnSuccessListener(qds -> {
            for (QueryDocumentSnapshot document: qds){
                Map<String,DocumentReference> references = (Map<String, DocumentReference>) document.get("my_volunteering");
                references.remove(v.getUid());
                document.getReference().update("my_volunteering", references);
            }
        });
    }

    public void getAllVolunteers(List<String> ids, OnSuccessListener<QuerySnapshot> os) {
        mCollection.whereIn("uid",ids).get().addOnSuccessListener(os);
    }
}
