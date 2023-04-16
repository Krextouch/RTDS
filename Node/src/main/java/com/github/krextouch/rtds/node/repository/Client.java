package com.github.krextouch.rtds.node.repository;

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

    public short[] curPos;

    public short[] destPos;

}
