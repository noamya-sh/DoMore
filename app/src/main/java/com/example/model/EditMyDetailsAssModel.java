package com.example.model;

import com.example.firebase.db.AssociationDB;
import com.example.firebase.db.UserDB;
import com.example.myapplication.EditMyDetailsAssActivity;
import com.example.myapplication.objects.Association;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

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
        if (phone.length()>0 && !Integer.toString(association.getPhone()).equals(phone)){
            association.setPhone(Integer.parseInt(phone));
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
