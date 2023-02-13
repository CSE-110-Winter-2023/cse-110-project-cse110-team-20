package com.example.socialcompass;

public class Point {
    float latitude;
    float longitude;
    String refName;
    public Point(){
        this.latitude = 0;
        this.longitude = 0;
        refName = "";
    }
    public Point(float latv, float longv, String namein){
        this.latitude = latv;
        this.longitude = longv;
        this.refName = namein;
    }
    public void setPoint(String textinput){
        if(textinput.indexOf(",") == -1){
            throw new RuntimeException("something is wrong with the input!");
        }
        String[] arrOfStr = textinput.split(",", 2);
        this.latitude = Float.parseFloat(arrOfStr[0]);
        this.longitude = Float.parseFloat(arrOfStr[1]);
    }
    public void setPoint(float latv, float longv) {
        this.latitude = latv;
        this.longitude = longv;
    }
}
