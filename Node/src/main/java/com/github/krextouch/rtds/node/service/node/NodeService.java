package com.github.krextouch.rtds.node.service.node;

import com.github.krextouch.rtds.node.repository.Client;
import com.github.krextouch.rtds.node.repository.ClientRepository;
import com.github.krextouch.rtds.node.service.model.SequenceGeneratorService;
import com.github.krextouch.rtds.node.service.trafficcontrol.Coordinate;
import com.github.krextouch.rtds.node.service.trafficcontrol.TrafficControlLogic;
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
        if(!clientRepository.existsById(clientId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ClientId not found");
        }
        Coordinate coorCurPos = new Coordinate(curPos[0], curPos[1]);
        Coordinate coorDestPos = new Coordinate(destPos[0], destPos[1]);

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
}
