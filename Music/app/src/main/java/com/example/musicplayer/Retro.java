package com.example.musicplayer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.Call;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface  Retro {
    String BASE_URL = "https://xsquare.net/";

    @GET("api.php")
    Call<List<ModelClass>> getdata();

}
