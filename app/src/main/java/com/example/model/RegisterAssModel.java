package com.example.model;

import com.example.firebase.db.AssociationDB;
import com.example.firebase.db.UserDB;
import com.example.myapplication.RegisterAssociationActivity;
import com.example.myapplication.objects.Association;

import java.util.HashMap;

public class RegisterAssModel {
    AssociationDB adb = new AssociationDB();
    RegisterAssociationActivity activity;

    public RegisterAssModel(RegisterAssociationActivity activity){
        this.activity = activity;
    }
    public void registerNewAssociation(String email,String pass,String name,String category,int phone){
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
