package com.marinemammalapp.dataObjects;

/**
 * Created by adheesh on 13/12/18.
 */

import com.google.gson.annotations.SerializedName;

/*
   "id": "382715",
		"created_date": "12-01-2019",
		"latitude": "78.0999",
		"longitude": "17.9009",
		"alive_status": "yes",
		"fin": "no",
		"beak": "yes",
		"color1": grey",
		"color2": grey",
		"comments": "it was 10 feet",

    */



public class MammalDetails {

    @SerializedName("id")
    String id;

    @SerializedName("username")
    String user_name;

    @SerializedName("phone_number")
    String phone_number;

    @SerializedName("created_date")
    String created_date;

    @SerializedName("latitude")
    String latitude;

    @SerializedName("longitude")
    String longitude;

    @SerializedName("alive_status")
    String alive_status;

    @SerializedName("fin")
    String fin;

    @SerializedName("beak")
    String beak;

    @SerializedName("color1")
    String color1;

    @SerializedName("color2")
    String color2;

    @SerializedName("comments")
    String comments;

    @SerializedName("imageurl")
    String imageurl;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAlive_status() {
        return alive_status;
    }

    public void setAlive_status(String alive_status) {
        this.alive_status = alive_status;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getBeak() {
        return beak;
    }

    public void setBeak(String beak) {
        this.beak = beak;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }



    @Override
    public String toString() {
        return "MammalDetails{" +
                "id='" + id + '\'' +
                "user_name"+user_name+'\''+
                "phone_number"+phone_number+'\''+
                ", created_date='" + created_date + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", alive_status='" + alive_status + '\'' +
                ", fin=" + fin +
                ", beak=" + beak +
                ", color1='" + color1 + '\'' +
                ", color2='" + color2 + '\'' +
                ", comments='" + comments + '\'' +
                ", imageurl='" + imageurl + '\'' +
                '}';
    }






}
