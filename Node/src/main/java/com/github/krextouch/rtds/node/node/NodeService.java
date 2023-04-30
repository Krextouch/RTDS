package com.github.krextouch.rtds.node.node;

import com.github.krextouch.rtds.node.model.SequenceGeneratorService;
import com.github.krextouch.rtds.node.repository.Client;
import com.github.krextouch.rtds.node.repository.ClientRepository;
import com.github.krextouch.rtds.node.trafficcontrol.Coordinate;
import com.github.krextouch.rtds.node.trafficcontrol.TrafficControlLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class NodeService {

    // manages all the client related database functions
    private final ClientRepository clientRepository;
    // sequence generator service used to generate auto-increment ids
    private final SequenceGeneratorService sequenceGeneratorService;
    // traffic control logic used for the next step needed for clients
    private final TrafficControlLogic trafficControlLogic;
    // environment manages the programm args
    private final Environment env;

    /**
     * the contructor
     *
     * @param clientRepository         Autowired
     * @param sequenceGeneratorService Autowired
     * @param trafficControlLogic      Autowired
     * @param env                      Autowired
     */
    @Autowired
    public NodeService(ClientRepository clientRepository,
                       SequenceGeneratorService sequenceGeneratorService,
                       TrafficControlLogic trafficControlLogic,
                       Environment env) {
        this.clientRepository = clientRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.trafficControlLogic = trafficControlLogic;
        this.env = env;
    }

    /**
     * creates a new client and returns its value to the client
     *
     * @return clientId
     */
    public short initClient() {
        // generates a new client id
        short clientId = sequenceGeneratorService.generateSequence(Client.SEQUENCE_NAME);
        // saves new client with dummy coordinates
        clientRepository.save(
                Client.builder()
                        .id(clientId)
                        .curPos(new Coordinate((short) -1, (short) -1))
                        .destPos(new Coordinate((short) -1, (short) -1))
                        .build()
        );
        // returns clientId to client
        return clientId;
    }

    /**
     * creates a new client and returns its value to the client
     *
     * @param clientId id of the client who wants to be moved
     * @param curPos   current position of the client
     * @param destPos  destination position of the client
     * @return coordinates as an array of x and y values
     */
    public short[] moveClient(short clientId, short[] curPos, short[] destPos) {
        // create coordinate objects from the coordinate arrays
        Coordinate coorCurPos = new Coordinate(curPos[0], curPos[1]);
        Coordinate coorDestPos = new Coordinate(destPos[0], destPos[1]);
        // check if client exists
        if (!clientRepository.existsById(clientId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ClientId not found");
        }
        // check if current coordinates are valid
        else if (checkIfCoordinatesAreInvalid(coorCurPos)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Current coordinates out of bounds");
        }
        // check if destination coordinates are valid
        else if (checkIfCoordinatesAreInvalid(coorDestPos)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Destination coordinates out of bounds");
        }

        // calculate next step
        Coordinate nextStep = trafficControlLogic.move(coorCurPos, coorDestPos);
        // save client with next step
        clientRepository.save(
                Client.builder()
                        .id(clientId)
                        .curPos(nextStep)
                        .destPos(coorDestPos)
                        .build()
        );
        // return the next step to the client
        return new short[]{nextStep.getX(), nextStep.getY()};
    }

    /**
     * checks if the coordinates are valid via approving if they are within the borders
     *
     * @param coor coordinate that needs to be checked
     * @return if returns true -> coordinates are invalid; else false
     */
    private boolean checkIfCoordinatesAreInvalid(Coordinate coor) {
        // get max area values from env
        short maxX = Short.parseShort(Objects.requireNonNull(env.getProperty("maxX")));
        short maxY = Short.parseShort(Objects.requireNonNull(env.getProperty("maxY")));

        // checking if the coordinates are within maxX and maxY
        if (coor.getX() >= 0 && coor.getX() < maxX
                && coor.getY() >= 0 && coor.getY() < maxY) {
            return false;
        }
        return true;
    }
}
