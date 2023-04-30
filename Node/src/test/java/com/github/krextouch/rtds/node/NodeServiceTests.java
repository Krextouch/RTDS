package com.github.krextouch.rtds.node;

import com.github.krextouch.rtds.node.model.SequenceGeneratorService;
import com.github.krextouch.rtds.node.node.NodeService;
import com.github.krextouch.rtds.node.repository.Client;
import com.github.krextouch.rtds.node.repository.ClientRepository;
import com.github.krextouch.rtds.node.trafficcontrol.Coordinate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

import static org.junit.Assert.*;

@AutoConfigureDataMongo
@RunWith(SpringRunner.class)
@SpringBootTest(args = {"--maxX=1000", "--maxY=1000", "--maxClientsPerCoordinate=2"})
public class NodeServiceTests {

    @Autowired
    NodeService nodeService;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    MongoTemplate mongoTemplate;

    @BeforeEach
    @AfterEach
    void setUp() {
        clientRepository.deleteAll();
        mongoTemplate.remove(new Query(), "database_sequences");
        clientRepository.save(
                Client.builder()
                        .id(sequenceGeneratorService.generateSequence(Client.SEQUENCE_NAME))
                        .curPos(new Coordinate((short) 0, (short) 0))
                        .destPos(new Coordinate((short) 0, (short) 0))
                        .build()
        );
    }

    @Test
    void testMoveClientCoordinatesOutOfBounds() {
        Exception exception = assertThrows(ResponseStatusException.class,
                () -> nodeService.moveClient((short) 1, new short[]{9999, 9999}, new short[]{9999, 9999}));

        String expectedMessage = "Current coordinates out of bounds";
        String actualMessage = exception.getMessage();

        org.junit.jupiter.api.Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testMoveClientDestionationOutOfBounds() {
        Exception exception = assertThrows(ResponseStatusException.class,
                () -> nodeService.moveClient((short) 1, new short[]{0, 0}, new short[]{9999, 9999}));

        String expectedMessage = "Destination coordinates out of bounds";
        String actualMessage = exception.getMessage();

        org.junit.jupiter.api.Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testClientIdNotFound() {
        Exception exception = assertThrows(ResponseStatusException.class,
                () -> nodeService.moveClient((short) 999, new short[]{9999, 9999}, new short[]{9999, 9999}));

        String expectedMessage = "ClientId not found";
        String actualMessage = exception.getMessage();

        org.junit.jupiter.api.Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetNextStep() {
        short[] nextStep = nodeService.moveClient((short) 1, new short[]{0, 0}, new short[]{3, 3});
        short[] expectedNextStep = new short[]{1, 1};

        assertArrayEquals(expectedNextStep, nextStep);
    }

    @Test
    void testGetNextStepInvalid() {
        short[] nextStep = nodeService.moveClient((short) 1, new short[]{0, 0}, new short[]{3, 3});
        short[] expectedNextStepInvalid = new short[]{1, 999};

        assertFalse(Arrays.equals(expectedNextStepInvalid, nextStep));
    }

    @Test
    void testInitClient() {
        assertEquals(2, nodeService.initClient());
    }
}
