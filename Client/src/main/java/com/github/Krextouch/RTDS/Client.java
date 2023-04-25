package com.github.Krextouch.RTDS;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

public class Client {

    private final String baseUrl = "http://localhost:8443/api/v1/";
    private final short clientId;
    private short[] curPos;
    private final short[] destPos;
    Random rand = new Random();

    HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public Client(short maxX, short maxY) {
        //Get clientId
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(baseUrl + "initClient"))
                    .build();

            HttpResponse<String> response;
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            clientId = Short.parseShort(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        curPos = new short[]{
                (short) rand.nextInt(maxX),
                (short) rand.nextInt(maxY)
        };
        destPos = new short[]{
                (short) rand.nextInt(maxX),
                (short) rand.nextInt(maxY)
        };
    }

    public void move(boolean debug) {
        Gson gson = new Gson();

        while (!(curPos[0] == destPos[0] && curPos[1] == destPos[1])) {
            try {
                HttpRequest request;
                request = HttpRequest.newBuilder()
                        .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(new Move(clientId, curPos, destPos))))
                        .uri(URI.create(baseUrl + "move"))
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .build();
                HttpResponse<String> response;
                response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                curPos = extractValues(response.body());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }


            if(Thread.currentThread().getName().equals("ClientThreadNr_0")
            && debug) {
                System.out.println("Progress: CurrentPos [" + curPos[0] + ":" + curPos[1] + "], DestPos [" + destPos[0] + ":" + destPos[1] + "]");
            }
        }
        if(debug) System.out.println("Reached goal. " + Thread.currentThread().getName());
    }

    private short[] extractValues(String responseString) {
        String shortendString = responseString.substring(1, responseString.length() - 1);

        return new short[]{
                Short.parseShort(shortendString.split(",")[0]),
                Short.parseShort(shortendString.split(",")[1])
        };
    }
}
