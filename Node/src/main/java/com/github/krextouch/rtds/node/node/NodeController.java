package com.github.krextouch.rtds.node.node;

import com.github.krextouch.rtds.node.model.MoveRequest;
import org.springframework.web.bind.annotation.*;

/**
 * rest-controller for defining api-interfaces
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
