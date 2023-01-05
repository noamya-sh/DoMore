package com.example.myapplication.model;

import com.example.myapplication.db.VolunteerDB;
import com.example.myapplication.db.VolunteeringDB;
import com.example.myapplication.activitiy.VolunteeringListActivity;
import com.example.myapplication.model.objects.Volunteer;
import com.example.myapplication.model.objects.Volunteering;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class VolunteeringListModel implements ListVolunteeringModel {
    private final VolunteeringDB vgdb = new VolunteeringDB();
    private final VolunteerDB vdb = new VolunteerDB();
    private Volunteer volunteer;
    private final VolunteeringListActivity activity;
    private boolean LIST_CHANGED = false;
    public ArrayList<Volunteering> volList = new ArrayList<>();

    public VolunteeringListModel(VolunteeringListActivity activity){
        this.activity = activity;
        initVolunteer();
    }
    private void initVolunteer() {
        vdb.getVolunteer(documentSnapshot -> volunteer = documentSnapshot.toObject(Volunteer.class));
    }
    public void getData(){
        vgdb.getListVolunteering(task -> {
            if (task.isSuccessful()) {
                activity.dismissLoadingDialog();
                for (QueryDocumentSnapshot document : task.getResult())
                    if (!volunteer.getMy_volunteering().containsKey(document.getId()) &&
                            document.getLong("num_vol_left") > 0) {
                        init_vol(document);
                    }
                activity.setData(volList);
            }
        });
    }
    private void init_vol(QueryDocumentSnapshot document) {
        Volunteering v = document.toObject(Volunteering.class);
        v.setUid(document.getId());
        volList.add(v);
    }
    public void search(Map<String,Object> query){
        ArrayList<Volunteering> newVol = new ArrayList<>(volList);
        if (query.containsKey("association")){
            String substring = (String) query.get("association");
            for (Volunteering v:volList){
                if (!v.getAssociation_name().toUpperCase().contains(substring.toUpperCase()))
                    newVol.remove(v);
            }
            LIST_CHANGED = true;
        }
        if (query.containsKey("category")){
            for (Volunteering v:volList){
                if (!v.getCategory().equals(query.get("category")))
                    newVol.remove(v);
            }
            LIST_CHANGED = true;
        }
        if (query.containsKey("from")){
            for (Volunteering v:volList){
                if (v.getStart().before((Date) query.get("from"))){
                    newVol.remove(v);
                }
            }
            LIST_CHANGED = true;
        }
        if (query.containsKey("un")){
            for (Volunteering v:volList){
                if (v.getStart().after((Date) query.get("un"))){
                    newVol.remove(v);
                }
            }
            LIST_CHANGED = true;
        }
        volList = newVol;
        activity.setData(volList);
    }
    public void addVolunteeringToVolunteer(Volunteering volunteering) {
        volunteer.getMy_volunteering().put(volunteering.getUid(),vgdb.getDocumentReference(volunteering));
        volunteering.getSignUpForVolunteering().put(volunteer.getUid(), vdb.getDocumentReference(volunteer));
        volunteering.setNum_vol_left(volunteering.getNum_vol_left()-1);

        vdb.setVolunteer(volunteer);
        vgdb.setVolunteering(volunteering);
    }
    public void removeVolunteering(Volunteering v) {
        volList.remove(v);
    }
    public void refresh() {
        if(LIST_CHANGED){
            volList.clear();
            activity.setData(volList);
            getData();
            LIST_CHANGED = false;
        }
    }

    @Override
    public void HandleMainChoose(Volunteering v) {
        addVolunteeringToVolunteer(v);
        removeVolunteering(v);
        activity.notifyAdapter();
    }
}
