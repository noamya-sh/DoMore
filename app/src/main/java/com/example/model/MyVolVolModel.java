package com.example.model;

import com.example.firebase.db.VolunteerDB;
import com.example.firebase.db.VolunteeringDB;
import com.example.myapplication.nocomplete.MyVolVolunteer;
import com.example.myapplication.objects.Volunteer;
import com.example.myapplication.objects.Volunteering;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyVolVolModel {
    VolunteeringDB vgdb = new VolunteeringDB();
    VolunteerDB vdb = new VolunteerDB();
    Volunteer volunteer;
    public ArrayList<Volunteering> volList = new ArrayList<>();
    MyVolVolunteer activity;

    public MyVolVolModel(MyVolVolunteer activity){
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
        System.out.println();
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
}
