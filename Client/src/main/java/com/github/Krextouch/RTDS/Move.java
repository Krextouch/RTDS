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

    public short getClientId() {
        return clientId;
    }

    public void setClientId(short clientId) {
        this.clientId = clientId;
    }

    public short[] getCurPos() {
        return curPos;
    }

    public void setCurPos(short[] curPos) {
        this.curPos = curPos;
    }

    public short[] getDestPos() {
        return destPos;
    }

    public void setDestPos(short[] destPos) {
        this.destPos = destPos;
    }
}
