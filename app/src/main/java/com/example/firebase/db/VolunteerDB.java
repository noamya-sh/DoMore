package com.example.firebase.db;
import com.example.myapplication.objects.Volunteer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class VolunteerDB extends FirebaseDB{
    CollectionReference mCollection = mDB.collection("volunteers");

    public void getName(OnCompleteListener<DocumentSnapshot> oc){
        mCollection.document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(oc);
    }

    public void registerVolunteer(Volunteer volunteer) {
        mCollection.document(volunteer.getUid()).set(volunteer);
    }
}
