package com.example.myapplication.model.apiserver;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {

    @FormUrlEncoded
    @POST("/")
    Call<ResponseBody> DocumentUID(@Field("ID") String ID);
}

