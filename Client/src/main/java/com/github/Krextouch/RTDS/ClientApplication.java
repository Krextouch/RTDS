package com.github.Krextouch.RTDS;

import java.util.HashMap;
import java.util.Map;

/**
 * application that starts a defined amount of clients
 * maxX, maxY, clients must be present; debug, timer are optional -> example configuration:
 * maxX=1000 maxY=1000 clients=100 debug=false timer=false
 */
public class ClientApplication {

    // timer variable used to store the start time if arg timer=true
    private static long timeStart;
    // variable used to count all the finished clients
    public static short finishedClients = 0;
    // variable used store the defined amount of clients for the finished-clients output
    public static short clients;

    public static void main(String[] args) {
        // splitting args into map
        Map<String, String> convertedArgs = new HashMap<>();
        for (String s : args) {
            String[] splitArgs = s.split("=");
            convertedArgs.put(splitArgs[0], splitArgs[1]);
        }

        // creating instance of clientThread
        ClientThread clientThread = new ClientThread(
                Short.parseShort(convertedArgs.get("maxX")),
                Short.parseShort(convertedArgs.get("maxY")),
                Short.parseShort(convertedArgs.get("clients")),
                Boolean.parseBoolean(convertedArgs.get("debug")),
                Boolean.parseBoolean(convertedArgs.get("timer")));

        clients = Short.parseShort(convertedArgs.get("clients"));
        // if timer=true store current time
        if (Boolean.parseBoolean(convertedArgs.get("timer"))) timeStart = System.currentTimeMillis();

        // start all clients
        clientThread.startClients();

        // if timer=true output elapsed time since start of all clients
        if (Boolean.parseBoolean(convertedArgs.get("timer"))) {
            long time = System.currentTimeMillis() - timeStart;
            System.out.println("Elapsed time: " + time + " ms");
        }
    }
}