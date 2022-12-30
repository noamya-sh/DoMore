package com.example.model;



import com.example.firebase.db.UserDB;
import com.example.myapplication.LoginActivity;
import com.google.firebase.firestore.DocumentSnapshot;



public class LoginModel {
    UserDB udb = new UserDB();
    LoginActivity activity;

    public LoginModel(LoginActivity activity){
        this.activity = activity;
    }

    public void login(String email, String pass){
        udb.login(email, pass, task -> {
            if (task.isSuccessful())
                udb.CheckUserType(task1 -> {
                    DocumentSnapshot document = task1.getResult();
                    if (document.exists())
                        activity.goVolHome();
                    else
                        activity.goAssHome();
                });
            else
                activity.FailureLogin();
        });
    }
}
