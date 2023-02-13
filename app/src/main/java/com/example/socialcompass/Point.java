package com.example.socialcompass;

public class Point {
    private float latitude;
    private float longitude;
    private String label;

    public Point(){
        this.latitude = 0;
        this.longitude = 0;
        label = "";
    }

    public Point(float latv, float longv){
        this.latitude = latv;
        this.longitude = longv;
        this.label = "";
    }

    public Point(float latv, float longv, String labelIn){
        this.latitude = latv;
        this.longitude = longv;
        this.label = labelIn;
    }

    public void setLocation(String textinput){
        if(textinput.indexOf(",") == -1){
            throw new RuntimeException("something is wrong with the input!");
        }
        String[] arrOfStr = textinput.split(",", 2);
        this.latitude = Float.parseFloat(arrOfStr[0]);
        this.longitude = Float.parseFloat(arrOfStr[1]);
    }

    public void setLocation(float latv, float longv) {
        this.latitude = latv;
        this.longitude = longv;
    }

    public void setLabel(String newLabel) {
        this.label = newLabel;
    }

    public float getLatitude() {
        return this.latitude;
    }

    public float getLongitude() {
        return this.longitude;
    }

    public String getLabel() {
        return this.label;
    }
}
