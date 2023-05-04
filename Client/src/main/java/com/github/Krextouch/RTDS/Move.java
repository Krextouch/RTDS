package com.github.Krextouch.RTDS;

/**
 * Move
 * move pojo
 * author:     inf20020@lehre.dhbw-stuttgart.de
 * date:       04.05.2023
 * version:    1.0
 */
public class Move {
    short clientId;
    short[] curPos;
    short[] destPos;

    public Move(short clientId, short[] curPos, short[] destPos) {
        this.clientId = clientId;
        this.curPos = curPos;
        this.destPos = destPos;
    }
}
