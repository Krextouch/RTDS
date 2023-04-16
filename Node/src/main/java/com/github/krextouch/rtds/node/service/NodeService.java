package com.github.krextouch.rtds.node.service;

import com.github.krextouch.rtds.node.client.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NodeService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    public NodeService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void hello() {

        System.out.println("Hello world");

    }

    public void moveClient(short currPos, short destPos) {
        System.out.println("Init");
    }

    public void moveClient(short clientId, short currPos, short destPos) {
        System.out.println("Move");
    }
}
