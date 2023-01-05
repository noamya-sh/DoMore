package com.example.myapplication.model;

import com.example.myapplication.db.AssociationDB;
import com.example.myapplication.db.UserDB;
import com.example.myapplication.activitiy.EditMyDetailsAssActivity;
import com.example.myapplication.model.objects.Association;

public class EditMyDetailsAssModel {
    EditMyDetailsAssActivity activity;
    AssociationDB adb = new AssociationDB();
    UserDB udb = new UserDB();
    Association association;

    public EditMyDetailsAssModel(EditMyDetailsAssActivity activity){
        this.activity = activity;
        initAssociation();
    }

    private void initAssociation() {
        adb.getAssociation(document -> {
            association = document.toObject(Association.class);
            activity.setDetails(association.getName(),association.getCategory(),
                    association.getEmail(),association.getPhone());
        });
    }
    public boolean getChanges(String name, String category, String phone, String password, String ver_pass){
        boolean changed = false;
        if (name.length()>0 && !association.getName().equals(name)){
            association.setName(name);
            changed = true;
        }
        if (category.length()>0 && !association.getCategory().equals(category)){
            association.setCategory(category);
            changed = true;
        }
        if (phone.length()>0 && association.getPhone().equals(phone)){
            association.setPhone(phone);
            changed = true;
        }
        if (password.length() > 0){
            if (!password.equals(ver_pass))
                return false;
            else{
                association.setPassword(password);
                udb.updatePassword(password);
                changed = true;
            }
        }
        if (changed)
            adb.setAssociation(association);
        return true;
    }
}
