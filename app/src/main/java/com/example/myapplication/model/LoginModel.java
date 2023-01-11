package com.example.myapplication.model;



import com.example.myapplication.db.UserDB;
import com.example.myapplication.activitiy.LoginActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginModel {
    UserDB udb = new UserDB();
    LoginActivity activity;

    public LoginModel(LoginActivity activity){
        this.activity = activity;
    }

    public void login(String email, String pass){
        udb.login(email, pass, task -> {
            if (task.isSuccessful()){
                udb.CheckUserTypeServer(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String s = response.body().string();
                            Gson gson = new Gson();
                            UserDB.VolunteerType VolunteerType = gson.fromJson(s, UserDB.VolunteerType.class);
                            if (VolunteerType.getType()){
                                activity.goVolHome();
                            }
                            else {
                                activity.goAssHome();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println("failure");
                    }
                });
            }
            else
                activity.FailureLogin();
        });
    }
}
