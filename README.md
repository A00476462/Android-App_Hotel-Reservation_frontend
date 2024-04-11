Introducation:
This program, built using Android Studio, implements GET POST APIs for reserving hotels.

## Please download Android Studio before running the program. You can download Android Studio on google.

## Please change url if necessary

change the base url: \java\com.example.hotelreservation\Api
baseUrl("http://django-hotel-api-final-project-dev.us-west-2.elasticbeanstalk.com/")

change url path: \java\com.example.hotelreservation\ApiInterface

@GET("/app/hotellist")
@POST("/app/reservation/")

## First page:

The first page is to input check-in & check-out date, guest number and guest name.
Click search button you will jump to next page.
Click clear button will clear all input infor.
Click confirm button will confirm what you input.
Click retrieve button will get what you confirmed last time so you don't have to input the same info again.

## Second page:

Get all hotels info from database and list on the page. You can click any hotel to jump into next page.

## Third page:

List the input rows to input guest info, including guest name, guest email, phone number and gender.
The number of guest depends on guest number in the first page.
Click reserve button will post the infor to database.

## Last page:

Return the confimation id from dsatabase when you create a new reservation.
