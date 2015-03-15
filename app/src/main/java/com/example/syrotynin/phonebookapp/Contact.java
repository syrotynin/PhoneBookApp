package com.example.syrotynin.phonebookapp;

/**
 * Created by Syrotynin on 11.03.2015.
 */
public class Contact {
    private String name;
    private String phoneNumber;
    private int id;

    public Contact(){
        this(1, "Default", "Default");
    }

    public Contact(int id, String name, String phoneNumber){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
