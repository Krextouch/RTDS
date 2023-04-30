package com.github.krextouch.rtds.node.trafficcontrol;

import com.github.krextouch.rtds.node.repository.Client;
import com.github.krextouch.rtds.node.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * the logic that can control the traffic
 */
@Component
public class TrafficControlLogic {

    // manages all the client related database functions
    private final ClientRepository clientRepository;
    // environment manages the programm args
    private final Environment env;

    /**
     * the contructor
     *
     * @param clientRepository -> Autowired
     * @param env              -> Autowired
     */
    @Autowired
    public TrafficControlLogic(ClientRepository clientRepository,
                               Environment env) {
        this.clientRepository = clientRepository;
        this.env = env;
    }

    /**
     * calculates the next step towards the target position; standing still is an option
     *
     * @param curPos  -> Coordinate current position
     * @param destPos -> Coordinate destination position
     * @return new Coordinate
     */
    public Coordinate move(Coordinate curPos, Coordinate destPos) {
        // calculate the next step around the current position
        Coordinate bestCoordinate = curPos;
        double distance = getDistance(bestCoordinate, destPos);

        // get max area values from env
        short maxX = Short.parseShort(Objects.requireNonNull(env.getProperty("maxX")));
        short maxY = Short.parseShort(Objects.requireNonNull(env.getProperty("maxY")));

        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {
                short x = (short) (curPos.getX() + xOffset);
                short y = (short) (curPos.getY() + yOffset);

                if (x < 0) x = 0;
                if (y < 0) y = 0;
                if (x > maxX - 1) x = (short) (maxX - 1);
                if (y > maxY - 1) y = (short) (maxY - 1);

                Coordinate coordinateToCheck = new Coordinate(x, y);
                if (isFree(coordinateToCheck)) {
                    double newDistance = getDistance(coordinateToCheck, destPos);
                    if (newDistance < distance) {
                        distance = newDistance;
                        bestCoordinate = coordinateToCheck;
                    }
                }
            }
        }
        return bestCoordinate;
    }

    /**
     * calculates the distance between 2 points
     *
     * @param firstCoordinate
     * @param secondCoordinate
     * @return the distance
     */
    private double getDistance(Coordinate firstCoordinate, Coordinate secondCoordinate) {
        double x1 = firstCoordinate.getX();
        double x2 = secondCoordinate.getX();
        double y1 = firstCoordinate.getY();
        double y2 = secondCoordinate.getY();

        if (x1 == x2 && y1 == y2)
            return 0.0;
        else {
            // calculate the distance
            return Math.sqrt(
                    Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)
            );
        }
    }

    /**
     * checks if the chosen step is free
     *
     * @param coorToCheck the coordinates of the chosen step
     * @return true if the node fields fewer clients than allowed acording to the arg maxClientsPerCoordinate
     */
    private boolean isFree(Coordinate coorToCheck) {
        // gets all clients at coordinate
        List<Client> clientsAtCoordinate = clientRepository.findClientByCurPos(coorToCheck);
        return clientsAtCoordinate.size() < Short.parseShort(Objects.requireNonNull(env.getProperty("maxClientsPerCoordinate")));
    }
}
