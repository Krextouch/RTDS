package com.github.Krextouch.RTDS;

import java.util.LinkedList;
import java.util.List;

public class ClientThread extends Thread {

    private final short maxX, maxY, clientCount;
    private final boolean debug, timer;

    public ClientThread(short maxX, short maxY, short clientCount, boolean debug, boolean timer) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.clientCount = clientCount;
        this.debug = debug;
        this.timer = timer;
    }

    public void startClients() {
        List<Thread> threadList = new LinkedList<>();

        for (int x = 0; x < clientCount; x++) {
            threadList.add(startThread(x));
        }

        if (timer) {
            for (Thread th : threadList) {
                try {
                    th.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private Thread startThread(int number) {
        Thread th = new Thread("ClientThreadNr_" + number) {
            public void run() {
                if (debug) System.out.println("ClientThreadNr_" + number + " started");
                Client client = new Client(maxX, maxY, debug);
                client.move(debug);
            }
        };
        th.start();
        return th;
    }
}
