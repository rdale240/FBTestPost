package com.example.rda6207.fbtestpost;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FBGraphApiClient {

        @NonNull
        public static Retrofit service = null;

        public static Retrofit FBGraphApiClient() {

            if(service == null) {
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                service = new Retrofit.Builder()
                        .baseUrl("https://graph.facebook.com/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
            }

            return service;
        }
}
