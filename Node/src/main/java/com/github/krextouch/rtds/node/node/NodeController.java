package com.github.krextouch.rtds.node.node;

import com.github.krextouch.rtds.node.model.MoveRequest;
import org.springframework.web.bind.annotation.*;

/**
 * NodeController
 * rest-controller for defining api-interfaces
 * author:     inf20020@lehre.dhbw-stuttgart.de
 * date:       04.05.2023
 * version:    1.0.0
 */
@RestController
@RequestMapping("/api/v1")
public interface NodeController {

    @GetMapping("/health")
    void health();

    @GetMapping("/initClient")
    short initClient();

    @PostMapping("/move")
    short[] move(@RequestBody MoveRequest moveRequest);
}
