package com.example.hotelreservation;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("/app/hotellist")
    public Call<List<HotelListData>> getHotelsLists();

    @POST("/app/reservation/")
    Call<Map<String, Integer>> createReservation(@Body ReservationData reservationData);
}
