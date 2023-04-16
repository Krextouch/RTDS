package com.github.krextouch.rtds.node.repository;

import com.github.krextouch.rtds.node.service.trafficcontrol.Coordinate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

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
