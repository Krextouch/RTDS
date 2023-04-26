package com.github.Krextouch.RTDS;

public class ClientThread {

    private final short maxX, maxY, clientCount;
    private final boolean debug;

    public ClientThread(short maxX, short maxY, short clientCount, boolean debug) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.clientCount = clientCount;
        this.debug = debug;
    }

    public void startClients() {
        for (int x = 0; x < clientCount; x++) {
            int finalX = x;
            new Thread(() -> {
                if (debug) System.out.println("ClientThreadNr_" + finalX + " started");
                Client client = new Client(maxX, maxY, debug);
                client.move(debug);
            }, "ClientThreadNr_" + x).start();
        }
    }
}
