package com.github.krextouch.rtds.node.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * move request pojo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoveRequest {

    private short clientId;

    private short[] curPos;

    private short[] destPos;
}