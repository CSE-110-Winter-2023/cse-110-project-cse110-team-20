package com.example.socialcompass;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class IntegrationTest {
    private static final String BASE_URL = "http://localhost:8080";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static MockServer mockServer;

    @BeforeClass
    public static void setUp() {
        mockServer = new MockServer(8080);
        mockServer.start();
        mockServer.stubPutAPI();
    }

    @AfterClass
    public static void tearDown() {
        mockServer.stop();
    }

    @Test
    public void testSendDataToServer() throws IOException {
        OkHttpClient client = new OkHttpClient();
        String json = "{\"name\":\"John Doe\", \"uid\":\"12345\", \"latitude\":37.7749, \"longitude\":-122.4194}";
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(BASE_URL + "/api/put")
                .put(body)
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());
    }
}
