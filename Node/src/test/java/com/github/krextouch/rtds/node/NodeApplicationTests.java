package com.github.krextouch.rtds.node;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest(args = {"--maxX=1000", "--maxY=1000", "--maxClientsPerCoordinate=2"})
class NodeApplicationTests {

    @Test
    public void testThatArgsAreNotEmpty(@Autowired Environment env) {
        Assertions.assertThat(env.getProperty("maxX")).isNotEmpty();
        Assertions.assertThat(env.getProperty("maxY")).isNotEmpty();
        Assertions.assertThat(env.getProperty("maxClientsPerCoordinate")).isNotEmpty();
    }
}
