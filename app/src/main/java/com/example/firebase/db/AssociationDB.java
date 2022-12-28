package com.example.firebase.db;

import com.example.myapplication.objects.Association;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class AssociationDB extends FirebaseDB {
    CollectionReference mCollection = mDB.collection("associations");
//    private Association a;

    public void addAssociationToDB(Association a){
//        this.a = a;

    }
    private void writeToDB(){

    }
    public void getName(OnCompleteListener<DocumentSnapshot> oc){
        mCollection.document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(oc);
    }
    public void registerAssociation(Association association){
        mCollection.document(association.getUid()).set(association);
    }
}
