package com.example.socialcompass;

public class Point {
    private double latitude;
    private double longitude;
    private String label;

    public Point(){
        this.latitude = 0;
        this.longitude = 0;
        label = "";
    }

    public Point(double latv, double longv){
        this.latitude = latv;
        this.longitude = longv;
        this.label = "";
    }

    public Point(double latv, double longv, String labelIn){
        this.latitude = latv;
        this.longitude = longv;
        this.label = labelIn;
    }

    public void setLocation(String textinput){
        if(textinput.indexOf(",") == -1){
            throw new RuntimeException("something is wrong with the input!");
        }
        String[] arrOfStr = textinput.split(",", 2);
        this.latitude = Double.parseDouble(arrOfStr[0]);
        this.longitude = Double.parseDouble(arrOfStr[1]);
    }

    public void setLocation(double latv, double longv) {
        this.latitude = latv;
        this.longitude = longv;
    }

    public void setLabel(String newLabel) {
        this.label = newLabel;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String getLabel() {
        return this.label;
    }
}
