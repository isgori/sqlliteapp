package com.example.sqlliteapp.models;

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