package com.github.Krextouch.RTDS;

import java.io.IOException;

public class ClientApplication {
    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client((short) 100, (short) 100);
        client.move(0);
    }
}