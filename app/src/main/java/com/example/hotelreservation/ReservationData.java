package com.example.hotelreservation;

import java.util.List;

public class ReservationData {
    private String hotel_name;
    private String checkin;
    private String checkout;
    private List<GuestData> guests_list;

    public ReservationData(String hotel_name, String checkin, String checkout, List guests_list) {
        this.hotel_name = hotel_name;
        this.checkin = checkin;
        this.checkout = checkout;
        this.guests_list = guests_list;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public List<GuestData> getGuests_list() {
        return guests_list;
    }

    public void setGuests_list(List<GuestData> guests_list) {
        this.guests_list = guests_list;
    }
}

