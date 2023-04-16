package com.github.krextouch.rtds.node.trafficcontrol;

import com.github.krextouch.rtds.node.NodeApplication;
import com.github.krextouch.rtds.node.repository.Client;
import com.github.krextouch.rtds.node.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * the logic that can control the traffic in the area
 */
@Component
public class TrafficControlLogic {

    private final ClientRepository clientRepository;

    @Autowired
    public TrafficControlLogic(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Coordinate move(Coordinate curPos, Coordinate destPos)
            throws ArrayIndexOutOfBoundsException {
        // calculate the next step around the current position
        Coordinate bestCoordinate = curPos;
        double distance = getDistance(bestCoordinate, destPos);
        short maxX = NodeApplication.getArgs().get("maxX");
        short maxY = NodeApplication.getArgs().get("maxY");

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

    private boolean isFree(Coordinate coorToCheck) {
        List<Client> clientsAtCoordinate = clientRepository.findClientByCurPos(coorToCheck);
        return clientsAtCoordinate.size() < 2;
    }
}
