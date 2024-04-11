package com.example.hotelreservation;

import android.util.Log;

import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;

//RestAdapter;
public class Api {
    //此方法返回一个ApiInterface类型的对象
    public static ApiInterface getClient(){

        //在这里修改自己的URL
        Retrofit retrofit =  new Retrofit.Builder()
                //.baseUrl("http://10.0.2.2:8000")
                .baseUrl("http://django-hotel-api-final-project-dev.us-west-2.elasticbeanstalk.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        return api;
    }

    public static void postReservationData(ReservationData data, Callback<Map<String, Integer>> callback){
        ApiInterface apiInterface = getClient();
        Call<Map<String, Integer>> call = apiInterface.createReservation(data);
        call.enqueue(callback);
    }
}

