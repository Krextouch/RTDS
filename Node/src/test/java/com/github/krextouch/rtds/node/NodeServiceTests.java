package com.github.krextouch.rtds.node;

import com.github.krextouch.rtds.node.node.NodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.Assert.assertThrows;

@SpringBootTest(args = {"--maxX=1000", "--maxY=1000", "--maxClientsPerCoordinate=2"})
public class NodeServiceTests {

    @Autowired
    NodeService nodeService;

    @Test
    void testMoveClientInvalidArguments() {
        Exception exception = assertThrows(ResponseStatusException.class,
                () -> nodeService.moveClient((short) 1, new short[]{9999, 9999}, new short[]{9999, 9999}));

        String expectedMessage = "Current coordinates out of bounds";
        String actualMessage = exception.getMessage();

        org.junit.jupiter.api.Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}
