package com.github.krextouch.rtds.node.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoveRequest {

    private short clientId;

    private short[] curPos;

    private short[] destPos;
}