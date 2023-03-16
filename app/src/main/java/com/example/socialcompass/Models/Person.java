package com.example.socialcompass.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "people")
public class Person {
    @PrimaryKey
    @SerializedName("label")
    @NonNull
    public String name;

    @SerializedName("public_code")
    @NonNull
    public String uid;

    @SerializedName("latitude")
    public float latitude;

    @SerializedName("longitude")
    public float longitude;

    /** General constructor for a note. */
    public Person() {}

    public Person(@NonNull String name, @NonNull String uid, float lat, float lon) {
        this.name = name;
        this.uid = uid;
        this.latitude = lat;
        this.longitude = lon;
    }

    public static Person fromJSON(String json) {
        // return new Person("", "", 0,0);
        return new Gson().fromJson(json, Person.class);
    }

    public Person changeLocation(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        return this;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name=" + name +
                ", uid='" + uid + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
