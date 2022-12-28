package com.example.model;

import androidx.annotation.NonNull;

import com.example.firebase.db.UserDB;
import com.example.myapplication.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class MainModel {
    UserDB udb = new UserDB();
    MainActivity activity;

    public MainModel(MainActivity activity){
        this.activity = activity;
    }

    public void navigation(){
        if (udb.isConnect())
            udb.CheckUserType(task -> {
                DocumentSnapshot document = task.getResult();
                if (document.exists())
                    activity.goVolHome();
                else
                    activity.goAssHome();
            });
        else
            this.activity.goLogin();
    }


}
