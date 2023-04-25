package com.github.Krextouch.RTDS;

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
