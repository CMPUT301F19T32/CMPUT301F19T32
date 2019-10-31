package com.example.myapplication;

public class Account {
    private String username;
    private String password;

    Account(String city, String province){
        this.username = city;
        this.password = province;
    }

    String getCityName(){
        return this.username;
    }

    String getProvinceName(){
        return this.password;
    }
}
