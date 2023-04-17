package com.github.Krextouch.RTDS;

public class ClientThread {

    private final short maxX, maxY, clientCount;

    public ClientThread(short maxX, short maxY, short clientCount) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.clientCount = clientCount;
    }

    public void startClients(boolean debug) {
        for (int x = 0; x < clientCount; x++) {
            int finalX = x;
            new Thread(() -> {
                if (debug) System.out.println("ClientThreadNr_" + finalX + " started");
                Client client = new Client(maxX, maxY);
                client.move(debug);
            }, "ClientThreadNr_" + x).start();
        }
    }
}
