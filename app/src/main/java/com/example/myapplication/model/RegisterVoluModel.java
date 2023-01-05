package com.example.myapplication.model;

import com.example.myapplication.db.UserDB;
import com.example.myapplication.db.VolunteerDB;
import com.example.myapplication.activitiy.RegisterVolunteerActivity;
import com.example.myapplication.model.objects.Volunteer;

import java.util.HashMap;


public class RegisterVoluModel {
    VolunteerDB vdb = new VolunteerDB();
    RegisterVolunteerActivity activity;

    public RegisterVoluModel(RegisterVolunteerActivity activity){
        this.activity = activity;
    }

    public void registerNewVolunteer(String email,String pass,String name,String city,String phone){
        UserDB udb = new UserDB();
        udb.registerNewUser(email, pass, task -> {
            if (task.isSuccessful()) {
                Volunteer v = new Volunteer(udb.getUID(),name,city,email,pass,phone,new HashMap<>());
                vdb.setVolunteer(v);
                activity.goHomeVolunteer();
            }
            else
                activity.registerFailed();
        });
    }
}
