package com.example.rda6207.fbtestpost;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FBGraphApiClientInterface {

    //User ID
        @GET("/100027783101552")
        Call<FBGetData> getInfo(@Query("fields") String fields, @Query("access_token") String at);
    //User ID
        @POST("/100027783101552/feed")
        Call<FBPostData> postItem(@Query("message") String fields, @Query("access_token") String at);


    }
