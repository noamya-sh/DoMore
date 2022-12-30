package com.example.model;
import com.example.firebase.db.UserDB;
import com.example.myapplication.MainActivity;
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
