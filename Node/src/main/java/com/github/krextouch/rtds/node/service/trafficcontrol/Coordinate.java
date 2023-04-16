package com.github.krextouch.rtds.node.service.trafficcontrol;

import lombok.Getter;

/**
 * simple coordinate class used
 * */
@Getter
public class Coordinate {
    private short x;
    private short y;

    public Coordinate(short x, short y) {
        this.x = x;
        this.y = y;
    }
}
