package com.example.model;

import com.example.firebase.db.UserDB;
import com.example.firebase.db.VolunteerDB;
import com.example.myapplication.EditMyDetailsVolActivity;
import com.example.myapplication.objects.Volunteer;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class EditMyDetailsVolModel {
    Volunteer volunteer;
    VolunteerDB vdb = new VolunteerDB();
    UserDB udb = new UserDB();
    EditMyDetailsVolActivity activity;

    public EditMyDetailsVolModel(EditMyDetailsVolActivity activity){
        this.activity = activity;
        initVolunteer();
    }

    private void initVolunteer() {
        vdb.getVolunteer(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot doc) {
                volunteer = doc.toObject(Volunteer.class);
            }
        });
    }
}
