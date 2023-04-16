package com.github.krextouch.rtds.node.service.node;

import com.github.krextouch.rtds.node.repository.Client;
import com.github.krextouch.rtds.node.repository.ClientRepository;
import com.github.krextouch.rtds.node.service.model.SequenceGeneratorService;
import com.github.krextouch.rtds.node.service.trafficcontrol.Coordinate;
import com.github.krextouch.rtds.node.service.trafficcontrol.TrafficControlLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void hello() {
        System.out.println("Hello world");
    }

    public short[] moveClient(short[] curPos, short[] destPos) {
        System.out.println("Init");
        short clientId = sequenceGeneratorService.generateSequence(Client.SEQUENCE_NAME);
        return moveClient(clientId, curPos, destPos);
    }

    public short[] moveClient(short clientId, short[] curPos, short[] destPos) {
        System.out.println("Move");
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

        return new short[] {nextStep.getX(),nextStep.getY()};
    }
}
