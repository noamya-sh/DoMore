package com.example.myapplication.model;

import com.example.myapplication.db.AssociationDB;
import com.example.myapplication.db.VolunteerDB;
import com.example.myapplication.db.VolunteeringDB;
import com.example.myapplication.activitiy.MyVolAssociationActivity;
import com.example.myapplication.model.objects.Association;
import com.example.myapplication.model.objects.Volunteering;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyVolAssModel {
    AssociationDB adb = new AssociationDB();
    VolunteeringDB vgdb = new VolunteeringDB();
    VolunteerDB vdb = new VolunteerDB();
    public ArrayList<Volunteering> volList = new ArrayList<>();
    Association association = new Association();
    MyVolAssociationActivity activity;

    public MyVolAssModel(MyVolAssociationActivity activity){
        this.activity = activity;
        initAssociation();
    }
    private void initAssociation() {
        adb.getAssociation(document -> {
            association = document.toObject(Association.class);
            getData();
        });
    }
    private void init_vol(QueryDocumentSnapshot document) {
        Volunteering v = document.toObject(Volunteering.class);
        v.setUid(document.getId());
        volList.add(v);
    }
    public void getData(){
        if(association.getMy_volunteering().size() > 0){
            List<String> ids = new ArrayList<>(association.getMy_volunteering().keySet());
            vgdb.getMyVolunteering(ids, qds -> {
                for (QueryDocumentSnapshot document: qds)
                    init_vol(document);
                activity.setData(volList);
            });
        }
        else
            activity.showNotExistVolunteering();
        activity.dismissDialog();
    }

    public void removeVolunteering(Volunteering v) {
        volList.remove(v);
        activity.notifyAdapter();
        association.removeVolunteering(v);
        vdb.removeVolunteeringFromAllVolunteers(v);
        vgdb.removeVolunteering(v);
        //Todo notifications
    }

    public String[] getAssociationMailAddress() {
        return new String[]{association.getEmail()};
    }

    public void sendMails(Volunteering volunteering) {
        List<String> ids = new ArrayList<>(volunteering.getSignUpForVolunteering().keySet());
        vdb.getAllVolunteers(ids, qds -> {
            List<String> emails = new ArrayList<>();
            for (QueryDocumentSnapshot document: qds){
                emails.add(document.getString("email"));
            }
            activity.sendMail(emails);
        });
    }

    public void sendSMSToVolunteers(Volunteering volunteering, String content) {
        List<String> ids = new ArrayList<>(volunteering.getSignUpForVolunteering().keySet());
        System.out.println("phone");
        vdb.getAllVolunteers(ids, qds -> {
            List<String> phones = new ArrayList<>();
            for (QueryDocumentSnapshot document: qds){
                phones.add(document.getString("phone"));
                System.out.println(document.getString("phone"));
            }
            activity.sendSMS(phones,content);
        });
    }
}
