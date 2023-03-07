package com.example.socialcompass;

import android.util.Log;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class LocationAPI {
    private volatile static LocationAPI instance = null;

    private OkHttpClient client;

    public LocationAPI() {
        this.client = new OkHttpClient();
    }

    public static LocationAPI provide() {
        if (instance == null) {
            instance = new LocationAPI();
        }
        return instance;
    }

    public void delete(String uid) {
        // URLs cannot contain spaces, so we replace them with %20.
        var request = new Request.Builder()
                .url("https://socialcompass.goto.ucsd.edu/location/" + uid)
                .method("DELETE", null)
                .build();

        try (var response = client.newCall(request).execute()) {
            assert response.body() != null;
            var body = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void put(String name, String uid, float latitude, float longitude) {
        // URLs cannot contain spaces, so we replace them with %20.
        name = name.replace(" ", "%20");

        String json = "{\"private_code\":\"" + uid +
                "\",\"label\":\"" + name +
                "\",\"latitude\":\"" + latitude +
                "\",\"longitude\":\"" + longitude + "\"}";

        MediaType JSON
                = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(json, JSON);

        var request = new Request.Builder()
                .url("https://socialcompass.goto.ucsd.edu/location/" + uid)
                .method("PUT", body)
                .build();

        try (var response = client.newCall(request).execute()) {
            assert response.body() != null;
            var responseData = response.body().string();
            Log.d("API-Test: put", responseData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * An example of sending a GET request to the server.
     *
     * The /echo/{msg} endpoint always just returns {"message": msg}.
     */
    public Person get(String uid) {
        // URLs cannot contain spaces, so we replace them with %20.
        var request = new Request.Builder()
                .url("https://socialcompass.goto.ucsd.edu/location/" + uid)
                .method("GET", null)
                .build();

        try (var response = client.newCall(request).execute()) {
            assert response.body() != null;
            var body = response.body().string();
            Log.d("API-Test: get", body);
            return Person.fromJSON(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Person("","",0,0);
    }
}
