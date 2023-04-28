package com.github.Krextouch.RTDS;

import java.util.HashMap;
import java.util.Map;

public class ClientApplication {

    private static long timeStart;
    public static short finishedClients = 0;
    public static short clients;

    public static void main(String[] args) {
        Map<String, String> convertedArgs = new HashMap<>();
        for (String s : args) {
            String[] splitArgs = s.split("=");
            convertedArgs.put(splitArgs[0], splitArgs[1]);
        }
        ClientThread clientThread = new ClientThread(
                Short.parseShort(convertedArgs.get("maxX")),
                Short.parseShort(convertedArgs.get("maxY")),
                Short.parseShort(convertedArgs.get("clients")),
                Boolean.parseBoolean(convertedArgs.get("debug")),
                Boolean.parseBoolean(convertedArgs.get("timer")));

        clients = Short.parseShort(convertedArgs.get("clients"));
        if (Boolean.parseBoolean(convertedArgs.get("timer"))) timeStart = System.currentTimeMillis();

        clientThread.startClients();

        if (Boolean.parseBoolean(convertedArgs.get("timer"))) {
            long time = System.currentTimeMillis() - timeStart;
            System.out.println("Elapsed time: " + time + " ms");
        }
    }
}