package com.example.model;

import com.example.firebase.db.AssociationDB;
import com.example.firebase.db.VolunteeringDB;
import com.example.myapplication.AddVolunteeringActivity;
import com.example.myapplication.objects.Association;
import com.example.myapplication.objects.Volunteering;
import java.util.Date;
import java.util.HashMap;

public class AddVolunteeringModel {
    VolunteeringDB vdb = new VolunteeringDB();
    AssociationDB adb = new AssociationDB();
    Association association;
    AddVolunteeringActivity activity;

    public AddVolunteeringModel(AddVolunteeringActivity activity){
        this.activity = activity;
        initAssociation();
    }

    private void initAssociation() {
        adb.getAssociation(documentSnapshot -> association = documentSnapshot.toObject(Association.class));
    }

    public void addNewVol(String title, String city, int phone, Date from, Date un, int num_volunteers){
        Volunteering volunteering = new Volunteering(vdb.generateNewID(),association.getName(),
                title,city, association.getCategory(), phone, from,un,
                adb.getRefernce(association.getUid()), num_volunteers, num_volunteers, new HashMap<>());
        vdb.setVolunteering(volunteering);
        association.getMy_volunteering().put(volunteering.getUid(),vdb.getDocumentReference(volunteering));
        adb.updateAssociation(association);
        activity.goToHome();
    }
}
