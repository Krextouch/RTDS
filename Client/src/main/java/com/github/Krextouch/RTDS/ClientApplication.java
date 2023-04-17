package com.github.Krextouch.RTDS;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ClientApplication {
    public static void main(String[] args) throws IOException, InterruptedException {
        Map<String, Short> convertedArgs = new HashMap<>();
        for (String s : args) {
            String[] splitArgs = s.split("=");
            convertedArgs.put(splitArgs[0], Short.valueOf(splitArgs[1]));
        }
        Client client = new Client(convertedArgs.get("maxX"), convertedArgs.get("maxY"));
        client.move(false);
    }
}