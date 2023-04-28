package com.github.krextouch.rtds.node;

import com.github.krextouch.rtds.node.model.SequenceGeneratorService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@AutoConfigureDataMongo
@SpringBootTest
public class ClientRepositoryTests {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    MongoTemplate mongoTemplate;

    private void setUp() {
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

    @BeforeEach
    public void beforeSetUp() {
        setUp();
    }

    @AfterEach
    public void afterSetUp() {
        setUp();
    }

    @Test
    public void shouldNotBeEmpty() {
        assertThat(clientRepository.findAll()).isNotEmpty();
    }

    @Test
    public void shouldContainOneClient() {
        assertEquals(1, clientRepository.findAll().size());
    }

    @Test
    public void existsById() {
        assertTrue(clientRepository.existsById((short) 1));
    }

    @Test
    public void findClientByCurPos() {
        assertEquals(1, clientRepository
                .findClientByCurPos(new Coordinate((short) 0, (short) 0))
                .size());
    }
}
