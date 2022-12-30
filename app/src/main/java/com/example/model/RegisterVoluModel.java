package com.example.model;

import com.example.firebase.db.UserDB;
import com.example.firebase.db.VolunteerDB;
import com.example.myapplication.RegisterVolunteerActivity;
import com.example.myapplication.objects.Volunteer;

import java.util.HashMap;


public class RegisterVoluModel {
    VolunteerDB vdb = new VolunteerDB();
    RegisterVolunteerActivity activity;

    public RegisterVoluModel(RegisterVolunteerActivity activity){
        this.activity = activity;
    }

    public void registerNewVolunteer(String email,String pass,String name,String city,int phone){
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
