package com.github.Krextouch.RTDS;

import java.util.HashMap;
import java.util.Map;

public class ClientApplication {
    public static void main(String[] args) {
        Map<String, String> convertedArgs = new HashMap<>();
        for (String s : args) {
            String[] splitArgs = s.split("=");
            convertedArgs.put(splitArgs[0], splitArgs[1]);
        }
        ClientThread clientThread = new ClientThread(Short.parseShort(convertedArgs.get("maxX")),
                Short.parseShort(convertedArgs.get("maxY")), Short.parseShort(convertedArgs.get("clients")));
        if (convertedArgs.size() > 3) {
            clientThread.startClients(Boolean.parseBoolean(convertedArgs.get("debug")));
        } else {
            clientThread.startClients(false);
        }
    }
}