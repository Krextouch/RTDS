package com.github.Krextouch.RTDS;

import java.util.LinkedList;
import java.util.List;

/**
 * ClientThread
 * class that creates x amount of clients and starts them as threads
 * author:     inf20020@lehre.dhbw-stuttgart.de
 * date:       04.05.2023
 * version:    1.0
 */
public class ClientThread extends Thread {

    // variables used to store programm arguments
    private final short maxX, maxY, clientCount;
    // variables used to store programm arguments
    private final boolean debug, timer;

    /**
     * the contructor; saves all the programm arguments
     *
     * @param maxX        max x value of the field
     * @param maxY        max y value of the field
     * @param clientCount amount of clients to be created
     * @param debug       debug enable/disable
     * @param timer       timer enable/disable
     */
    public ClientThread(short maxX, short maxY, short clientCount, boolean debug, boolean timer) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.clientCount = clientCount;
        this.debug = debug;
        this.timer = timer;
    }

    /**
     * manage all client threads
     */
    public void startClients() {
        // List containing all threads needed for timer function
        List<Thread> threadList = new LinkedList<>();

        // create and start client threads
        for (int x = 0; x < clientCount; x++) {
            threadList.add(startThread(x));
        }

        // if timer=true wait for threads to finish -> time can be measured
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

    /**
     * initiates and starts client threads
     */
    private Thread startThread(int number) {
        // create new thread
        Thread th = new Thread("ClientThreadNr_" + number) {
            public void run() {
                if (debug) System.out.println("ClientThreadNr_" + number + " started");
                // create new client
                Client client = new Client(maxX, maxY, debug);
                // start new client
                client.move();
            }
        };
        // start thread
        th.start();
        return th;
    }
}
