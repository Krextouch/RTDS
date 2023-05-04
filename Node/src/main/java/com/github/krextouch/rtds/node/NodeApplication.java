package com.github.krextouch.rtds.node;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * NodeApplication
 * spring application that starts the Road Traffic Divert System
 * all args must be present -> example configuration:
 * --maxX=1000 --maxY=1000 --maxClientsPerCoordinate=2
 * author:     inf20020@lehre.dhbw-stuttgart.de
 * date:       04.05.2023
 * version:    1.0.0
 */
@SpringBootApplication
public class NodeApplication {
    public static void main(String[] args) {
        SpringApplication.run(NodeApplication.class, args);
    }
}
