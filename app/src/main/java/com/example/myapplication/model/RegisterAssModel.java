package com.example.myapplication.model;

import com.example.myapplication.db.AssociationDB;
import com.example.myapplication.db.UserDB;
import com.example.myapplication.activitiy.RegisterAssociationActivity;
import com.example.myapplication.model.objects.Association;

import java.util.HashMap;

public class RegisterAssModel {
    AssociationDB adb = new AssociationDB();
    RegisterAssociationActivity activity;

    public RegisterAssModel(RegisterAssociationActivity activity){
        this.activity = activity;
    }
    public void registerNewAssociation(String email,String pass,String name,String category,String phone){
        UserDB udb = new UserDB();
        udb.registerNewUser(email, pass, task -> {
            if (task.isSuccessful()) {
                Association a = new Association(udb.getUID(),name,email,pass,category,phone,new HashMap<>());
                adb.setAssociation(a);
                activity.goHomeAssociation();
            }
            else
                activity.registerFailed();
        });
    }

}
