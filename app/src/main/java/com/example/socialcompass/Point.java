package com.example.socialcompass;

public class Point {
    public double latitude;
    public double longitude;
    public String refName;
    public Point(){
        this.latitude = 0;
        this.longitude = 0;
        refName = "";
    }
    public Point(double latv, double longv, String namein){
        this.latitude = latv;
        this.longitude = longv;
        this.refName = namein;
    }
    public void setPoint(String textinput){
        if(textinput.indexOf(",") == -1){
            throw new RuntimeException("something is wrong with the input!");
        }
        String[] arrOfStr = textinput.split(",", 2);
        this.latitude = Double.parseDouble(arrOfStr[0]);
        this.longitude = Double.parseDouble(arrOfStr[1]);
    }
    public void setPoint(double latv, double longv) {
        this.latitude = latv;
        this.longitude = longv;
    }
}
