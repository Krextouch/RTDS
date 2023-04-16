package com.github.krextouch.rtds.node.node;

import com.github.krextouch.rtds.node.NodeApplication;
import com.github.krextouch.rtds.node.repository.Client;
import com.github.krextouch.rtds.node.repository.ClientRepository;
import com.github.krextouch.rtds.node.model.SequenceGeneratorService;
import com.github.krextouch.rtds.node.trafficcontrol.Coordinate;
import com.github.krextouch.rtds.node.trafficcontrol.TrafficControlLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class NodeService {

    private final ClientRepository clientRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final TrafficControlLogic trafficControlLogic;

    @Autowired
    public NodeService(ClientRepository clientRepository,
                       SequenceGeneratorService sequenceGeneratorService,
                       TrafficControlLogic trafficControlLogic) {
        this.clientRepository = clientRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.trafficControlLogic = trafficControlLogic;
    }

    public short initClient() {
        return sequenceGeneratorService.generateSequence(Client.SEQUENCE_NAME);
    }

    public short[] moveClient(short clientId, short[] curPos, short[] destPos) {
        Coordinate coorCurPos = new Coordinate(curPos[0], curPos[1]);
        Coordinate coorDestPos = new Coordinate(destPos[0], destPos[1]);
        if(!clientRepository.existsById(clientId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ClientId not found");
        } else if(checkIfCoordinatesAreInvalid(coorCurPos)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Current coordinates out of bounds");
        } else if(checkIfCoordinatesAreInvalid(coorDestPos)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Destination coordinates out of bounds");
        }

        Coordinate nextStep = trafficControlLogic.move(coorCurPos, coorDestPos);
        clientRepository.save(
                Client.builder()
                        .id(clientId)
                        .curPos(nextStep)
                        .destPos(coorDestPos)
                        .build()
        );
        return new short[]{nextStep.getX(), nextStep.getY()};
    }

    private boolean checkIfCoordinatesAreInvalid(Coordinate coor) {
        short maxX = NodeApplication.getArgs().get("maxX");
        short maxY = NodeApplication.getArgs().get("maxY");

        if(coor.getX() >= 0 && coor.getX() < maxX
                && coor.getY() >= 0 && coor.getY() < maxY) {
            return false;
        }
        return true;
    }
}
