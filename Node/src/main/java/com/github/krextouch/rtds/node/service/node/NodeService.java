package com.github.krextouch.rtds.node.service.node;

import com.github.krextouch.rtds.node.repository.Client;
import com.github.krextouch.rtds.node.repository.ClientRepository;
import com.github.krextouch.rtds.node.service.model.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NodeService {

    private final ClientRepository clientRepository;

    private final SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public NodeService(ClientRepository clientRepository, SequenceGeneratorService sequenceGeneratorService) {
        this.clientRepository = clientRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    public void hello() {

        System.out.println("Hello world");

    }

    public void moveClient(short[] curPos, short[] destPos) {
        System.out.println("Init");

        clientRepository.save(
                Client.builder()
                        .id(sequenceGeneratorService.generateSequence(Client.SEQUENCE_NAME))
                        .curPos(curPos)
                        .destPos(destPos)
                        .build()
        );
    }

    public void moveClient(short clientId, short[] curPos, short[] destPos) {
        System.out.println("Move");

        clientRepository.save(
                Client.builder()
                        .id(clientId)
                        .curPos(curPos)
                        .destPos(destPos)
                        .build()
        );
    }
}
