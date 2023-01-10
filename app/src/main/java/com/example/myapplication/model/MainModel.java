package com.example.myapplication.model;
import com.example.myapplication.model.apiserver.RegisterAPI;
import com.example.myapplication.db.UserDB;
import com.example.myapplication.activitiy.MainActivity;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainModel {
    UserDB udb = new UserDB();
    MainActivity activity;

    public MainModel(MainActivity activity){
        this.activity = activity;
    }

    public void navigation(){
        if (udb.isConnect()) {
            CheckUserTypeServer(udb.getUID());
//            udb.CheckUserType(task -> {
//                DocumentSnapshot document = task.getResult();
//                if (document.exists())
//                    activity.goVolHome();
//                else
//                    activity.goAssHome();
//            });
        }else
            this.activity.goLogin();
    }

    public void CheckUserTypeServer(String uid){
        Call<ResponseBody> call = RegisterAPI.getInstance().getAPI().DocumentUID(uid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string();
                    Gson gson = new Gson();
                    VolunteerType VolunteerType = gson.fromJson(s, VolunteerType.class);
                    if (VolunteerType.type){
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
    private static class VolunteerType {
        Boolean type;
        public VolunteerType(){}
        public VolunteerType(Boolean type){ this.type = type; }
        public Boolean getType() {
            return type;
        }
        public void setType(Boolean type) {
            this.type = type;
        }
    }
}
