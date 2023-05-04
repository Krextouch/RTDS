package com.github.krextouch.rtds.node.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MoveRequest
 * MoveRequest pojo
 * author:     inf20020@lehre.dhbw-stuttgart.de
 * date:       04.05.2023
 * version:    1.0.0
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