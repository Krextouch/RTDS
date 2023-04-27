package com.github.krextouch.rtds.node;

import com.github.krextouch.rtds.node.repository.Client;
import com.github.krextouch.rtds.node.repository.ClientRepository;
import com.github.krextouch.rtds.node.trafficcontrol.Coordinate;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataMongoTest
public class ClientRepositoryTests {

    @Autowired
    ClientRepository clientRepository;

    @Before
    public void setUp() {
        clientRepository.save(
                Client.builder()
                        .id((short) 1)
                        .curPos(new Coordinate((short) 0, (short) 0))
                        .destPos(new Coordinate((short) 0, (short) 0))
                        .build()
        );
    }

    @Test
    public void shouldNotBeEmpty() {
        assertThat(clientRepository.findAll()).isNotEmpty();
    }
}
