package com.example.model;

import com.example.firebase.db.AssociationDB;
import com.example.firebase.db.VolunteeringDB;
import com.example.myapplication.EditMyVolAssActivity;
import com.example.myapplication.objects.Volunteering;
import java.util.Date;

public class EditMyVolAssModel {
    EditMyVolAssActivity activity;
    VolunteeringDB vdb = new VolunteeringDB();
    AssociationDB adb = new AssociationDB();

    public EditMyVolAssModel(EditMyVolAssActivity activity){
        this.activity = activity;
    }

    public void saveChanges(String id,String title, String city, int num_vol, Date start, Date end, String phone) {
        vdb.getVolunteering(id, doc -> {
            Volunteering v = doc.toObject(Volunteering.class);

            assert v != null;
            v.setTitle(title);
            v.setLocation(city);
            v.setStart(start);
            v.setEnd(end);
            v.setPhone(phone);
            int regs = v.getNum_vol() - v.getNum_vol_left();
            if (num_vol >= regs){
                v.setNum_vol(num_vol);
                v.setNum_vol_left(num_vol-regs);
            }
            else{
               //toDo Delete random volunteers
                System.out.println("throw volunteers...");
            }
            vdb.setVolunteering(v);

        });
    }
}
