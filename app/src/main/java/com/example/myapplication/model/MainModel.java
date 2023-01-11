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
}
