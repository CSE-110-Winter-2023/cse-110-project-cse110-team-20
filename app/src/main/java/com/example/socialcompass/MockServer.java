package com.example.socialcompass;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MockServer {
    private final WireMockServer wireMockServer;

    public MockServer(int port) {
        this.wireMockServer = new WireMockServer(port);
        configureFor("localhost", port);
    }

    public void start() {
        wireMockServer.start();
    }

    public void stop() {
        wireMockServer.stop();
    }

    public void stubPutAPI() {
        stubFor(put(urlEqualTo("/api/put"))
                .willReturn(aResponse()
                        .withStatus(200)));
    }
}

