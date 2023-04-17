package com.github.Krextouch.RTDS;

import java.net.URI;
import java.net.http.HttpRequest;

public class Client {

    private final String baseUrl = "http://localhost:8443/api/v1/";

    public Client() {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(baseUrl + "initClient"))
                .build();

        System.out.println("---" + request + "---");
    }
}
