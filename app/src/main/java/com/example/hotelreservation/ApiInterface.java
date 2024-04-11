package com.example.hotelreservation;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("/app/hotellist")
    //返回一个call类型对象，包含List<HotelListData>类型的数据
    //getHotelsLists这是方法名，需要传参，参数是Callback<List<HotelListData>> callback
    public Call<List<HotelListData>> getHotelsLists();

    @POST("/app/reservation/")
    Call<Map<String, Integer>> createReservation(@Body ReservationData reservationData);
}
