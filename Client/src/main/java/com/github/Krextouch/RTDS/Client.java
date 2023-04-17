package com.github.Krextouch.RTDS;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.json.JSONObject;

public class Client {

    private final String baseUrl = "http://localhost:8443/api/v1/";
    private final short clientId;
    private short[] curPos;
    private short[] destPos;

    HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public Client(short maxX, short maxY) throws IOException, InterruptedException {
        //Get clientId
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(baseUrl + "initClient"))
                .build();

        HttpResponse<String> response = null;
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        clientId = Short.parseShort(response.body());

        Random rand = new Random(System.currentTimeMillis());
        curPos = new short[]{
                (short) rand.nextInt(maxX),
                (short) rand.nextInt(maxY)
        };
        destPos = new short[]{
                (short) rand.nextInt(maxX),
                (short) rand.nextInt(maxY)
        };
    }

    public void move(float sleepTimer) {


        JSONObject json = new JSONObject();
        json.put("clientId", clientId);
        json.put("curPos", curPos);
        json.put("destPos", destPos);

        System.out.println(json.toString());

        try {
            HttpRequest request = null;
            request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                    .uri(URI.create(baseUrl + "move"))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = null;
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        while(!(curPos[0] == destPos[0] && curPos[1] == destPos[1])) {
//
//        }
    }
}
