package com.example.hotelreservation;

public class HotelListData {
    String name;
    String price;
    String availability;

    public HotelListData(String hotel_name, String price, String availability){
        this.name = hotel_name;
        this.price = price;
        this.availability = availability;
    }

    public String getHotel_name() {
        return name;
    }

    public void setHotel_name(String hotel_name) {
        this.name = hotel_name;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getAvailability() {
        return availability;
    }
    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
