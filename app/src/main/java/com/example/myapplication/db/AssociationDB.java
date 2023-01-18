package com.example.myapplication.db;
import com.example.myapplication.model.objects.Association;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

//for get, set and update data form association collection in firestore
public class AssociationDB extends FirebaseDB {
    CollectionReference mCollection = mDB.collection("associations");

    public void getName(OnCompleteListener<DocumentSnapshot> oc){
        mCollection.document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(oc);
    }
    public void setAssociation(Association association){
        mCollection.document(association.getUid()).set(association);
    }
    public DocumentReference getRefernce(String id){ return mCollection.document(id);}
    public void getAssociation(OnSuccessListener<DocumentSnapshot> os) {
        mCollection.document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(os);
    }

    public void updateAssociation(Association association) {
        mCollection.document(association.getUid()).set(association);
    }
}
