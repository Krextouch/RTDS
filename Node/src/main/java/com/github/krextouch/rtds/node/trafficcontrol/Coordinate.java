package com.github.krextouch.rtds.node.trafficcontrol;

import lombok.Getter;

/**
 * Coordinate
 * simple coordinate class used
 * author:     inf20020@lehre.dhbw-stuttgart.de
 * date:       04.05.2023
 * version:    1.0.0
 */
@Getter
public class Coordinate {
    private short x;
    private short y;

    public Coordinate(short x, short y) {
        this.x = x;
        this.y = y;
    }
}
