package com.example.model;

import com.example.firebase.db.AssociationDB;
import com.example.myapplication.EditMyDetailsAssActivity;
import com.example.myapplication.objects.Association;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class EditMyDetailsAssModel {
    EditMyDetailsAssActivity activity;
    AssociationDB adb = new AssociationDB();
    Association association;

    public EditMyDetailsAssModel(EditMyDetailsAssActivity activity){
        this.activity = activity;
        initAssociation();
    }

    private void initAssociation() {
        adb.getAssociation(document -> {
            association = document.toObject(Association.class);
            activity.setDetails(association.getName(),association.getCategory(),
                    association.getEmail(),association.getPhone());
        });
    }
}
