package com.example.myapplication.model;

import com.example.myapplication.db.VolunteerDB;
import com.example.myapplication.db.VolunteeringDB;
import com.example.myapplication.activitiy.MyVolVolunteerActivity;
import com.example.myapplication.model.objects.Volunteer;
import com.example.myapplication.model.objects.Volunteering;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyVolVolModel implements ListVolunteeringModel {
    VolunteeringDB vgdb = new VolunteeringDB();
    VolunteerDB vdb = new VolunteerDB();
    Volunteer volunteer;
    public ArrayList<Volunteering> volList = new ArrayList<>();
    MyVolVolunteerActivity activity;

    public MyVolVolModel(MyVolVolunteerActivity activity){
        this.activity = activity;
        initVolunteer();
    }

    private void initVolunteer() {
        vdb.getVolunteer(document -> {
            volunteer = document.toObject(Volunteer.class);
            getData();
        });
    }
    public void getData(){
        activity.showDialog();
        if(volunteer.getMy_volunteering().size() > 0){
            List<String> ids = new ArrayList<>(volunteer.getMy_volunteering().keySet());
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

    private void init_vol(QueryDocumentSnapshot document) {
        Volunteering v = document.toObject(Volunteering.class);
        v.setUid(document.getId());
        volList.add(v);
    }

    public void removeVolunteeringFromVolunteer(Volunteering v) {
        volList.remove(v);
        activity.notifyAdapter();
        volunteer.removeVolunteering(v);
        v.setNum_vol_left(v.getNum_vol_left()+1);
        v.getSignUpForVolunteering().remove(volunteer.getUid());
        vgdb.setVolunteering(v);
        vdb.setVolunteer(volunteer);
    }

    @Override
    public void HandleMainChoose(Volunteering v) {
        removeVolunteeringFromVolunteer(v);
        activity.notifyAdapter();
    }
}
