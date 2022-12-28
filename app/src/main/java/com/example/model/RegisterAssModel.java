package com.example.model;

import androidx.annotation.NonNull;

import com.example.firebase.db.AssociationDB;
import com.example.firebase.db.UserDB;
import com.example.myapplication.RegisterAssociationActivity;
import com.example.myapplication.objects.Association;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

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
                adb.registerAssociation(a);
                activity.goHomeAssociation();
            }
            else
                activity.registerFailed();
        });
    }

}
