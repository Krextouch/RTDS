package com.github.Krextouch.RTDS;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

/**
 * client that sends request till reaching its destination
 */
public class Client {

    // url to the caddy reverse proxy
    private final String baseUrl = "http://localhost:9000/api/v1/";
    // time for the timeout after an exception
    private final byte sleepTime = 15;
    // clientId received from the node
    private short clientId;
    // current position
    private short[] curPos;
    // destination position
    private final short[] destPos;
    // debug enable/disable
    private final boolean debug;
    Random rand = new Random();
    HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    /**
     * the contructor; starts all single-time routines
     *
     * @param maxX  max x value of the field
     * @param maxY  max y value of the field
     * @param debug debug enable/disable
     */
    public Client(short maxX, short maxY, boolean debug) {
        this.debug = debug;
        boolean caddyIsNotReachable = false;

        // try till clientId is retrieved
        while (!caddyIsNotReachable) {
            try {
                // http-request to initClient
                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create(baseUrl + "initClient"))
                        .build();

                HttpResponse<String> response;
                response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                // if request returns with an exception -> try again
                if (response.statusCode() != 200 &&
                        response.statusCode() != 201 &&
                        response.statusCode() != 202) throw new Exception();
                // save clientId
                clientId = Short.parseShort(response.body());

                caddyIsNotReachable = true;
            } catch (Exception e) {
                handleException();
            }
        }

        // generate current position within borders
        curPos = new short[]{
                (short) rand.nextInt(maxX),
                (short) rand.nextInt(maxY)
        };
        // generate destination position within borders
        destPos = new short[]{
                (short) rand.nextInt(maxX),
                (short) rand.nextInt(maxY)
        };
    }

    /**
     * moves the client till reaching its destination
     */
    public void move() {
        Gson gson = new Gson();

        // as long as the destination is not reached
        while (!(curPos[0] == destPos[0] && curPos[1] == destPos[1])) {
            boolean caddyIsNotReachable = false;

            // try till move step is successfully
            while (!caddyIsNotReachable) {
                try {
                    // http-request to move
                    HttpRequest request;
                    request = HttpRequest.newBuilder()
                            .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(new Move(clientId, curPos, destPos))))
                            .uri(URI.create(baseUrl + "move"))
                            .header("Content-Type", "application/json")
                            .header("Accept", "application/json")
                            .build();
                    HttpResponse<String> response;
                    response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    // if request returns with an exception -> try again
                    if (response.statusCode() != 200 &&
                            response.statusCode() != 201 &&
                            response.statusCode() != 202) throw new Exception();
                    curPos = extractValues(response.body());

                    caddyIsNotReachable = true;
                } catch (Exception e) {
                    handleException();
                }
            }
        }
        // if debug=true output which client finished
        if (debug) System.out.println("Reached goal. " + Thread.currentThread().getName());

        // output how many clients have finished
        ClientApplication.finishedClients++;
        System.out.println(ClientApplication.finishedClients + "/" + ClientApplication.clients
                + " client/s reached the goal.");
    }

    /**
     * extracts current position from a response string
     *
     * @param responseString string were current position needs to be extracted from
     * @return current position as short array
     */
    private short[] extractValues(String responseString) {
        // remove unnecessary chars
        String shortendString = responseString.substring(1, responseString.length() - 1);

        return new short[]{
                Short.parseShort(shortendString.split(",")[0]),
                Short.parseShort(shortendString.split(",")[1])
        };
    }

    /**
     * handles exception via putting the client in a timeout for a retry
     */
    private void handleException() {
        byte sleepDuration = randomSleepTime();
        // if debug=true output which client has a timeout for how long
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

    /**
     * generated a random time according to the defined sleepTime variable
     *
     * @return random sleep time as byte
     */
    private byte randomSleepTime() {
        return (byte) (rand.nextInt(sleepTime) + sleepTime);
    }
}
