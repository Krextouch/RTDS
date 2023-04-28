package com.github.Krextouch.RTDS;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

public class Client {

    private final String baseUrl = "http://localhost:9000/api/v1/";
    private final byte sleepTime = 15;
    private short clientId;
    private short[] curPos;
    private final short[] destPos;
    private final boolean debug;
    Random rand = new Random();

    HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public Client(short maxX, short maxY, boolean debug) {
        this.debug = debug;
        boolean caddyIsNotReachable = false;

        //Get clientId
        while (!caddyIsNotReachable) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create(baseUrl + "initClient"))
                        .build();

                HttpResponse<String> response;
                response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 500) throw new Exception();
                clientId = Short.parseShort(response.body());

                caddyIsNotReachable = true;
            } catch (Exception e) {
                handleConnectException();
            }
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
            boolean caddyIsNotReachable = false;

            while (!caddyIsNotReachable) {
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
                    if (response.statusCode() == 500) throw new Exception();
                    curPos = extractValues(response.body());

                    caddyIsNotReachable = true;
                } catch (Exception e) {
                    handleConnectException();
                }
            }
        }
        if (debug) System.out.println("Reached goal. " + Thread.currentThread().getName());
    }

    private short[] extractValues(String responseString) {
        String shortendString = responseString.substring(1, responseString.length() - 1);

        return new short[]{
                Short.parseShort(shortendString.split(",")[0]),
                Short.parseShort(shortendString.split(",")[1])
        };
    }

    private void handleConnectException() {
        byte sleepDuration = randomSleepTime();
        if (debug) {
            System.out.println(Thread.currentThread().getName() + " had a an exception... waiting "
                    + sleepDuration + " seconds for a retry");
        }
        try {
            Thread.sleep(sleepDuration * 1000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    private byte randomSleepTime() {
        return (byte) (rand.nextInt(sleepTime) + sleepTime);
    }
}
