package com.example.shoppershub.Model;

import java.util.ArrayList;

public class LoginModel {
    ArrayList<loginData> data;
    String message;

    public LoginModel() {
    }

    public LoginModel(ArrayList<loginData> data, String message) {
        this.data = data;
        this.message = message;
    }

    public ArrayList<loginData> getData() {
        return data;
    }

    public void setData(ArrayList<loginData> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}