package com.github.krextouch.rtds.node.repository;

import com.github.krextouch.rtds.node.trafficcontrol.Coordinate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

/**
 * Client
 * client entity definition
 * author:     inf20020@lehre.dhbw-stuttgart.de
 * date:       04.05.2023
 * version:    1.0.0
 */
@Builder
@Getter
@Setter
public class Client {

    @Transient
    public static final String SEQUENCE_NAME = "client_sequence";

    @Id
    public short id;

    public Coordinate curPos;

    public Coordinate destPos;
}
