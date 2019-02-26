package com.example.sqlliteapp.models;

import java.util.ArrayList;

public  class User{
    private String id;
    private String name;
    private String last;
    private String postcode;
    private String street;
    private String state;
    private String city;
    private String email;
    private String user;
    private String birthday;
    private String phone;
    private String cell;
    private String picture;

    public static ArrayList<User> dataDB = new ArrayList<User>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public User(String  ... arg ) {
        this.id= arg[0];
        this.name= arg[1];
        this.last=arg[2];
        this.postcode=arg[3];
        this.street=arg[4];
        this.state=arg[5];
        this.city=arg[6];
        this.email=arg[7];
        this.user=arg[8];
        this.birthday=arg[9];
        this.phone=arg[10];
        this.cell=arg[11];
        this.picture=arg[12];

    }
}