package com.github.krextouch.rtds.node.service.node;

import com.github.krextouch.rtds.node.service.model.MoveRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public interface NodeController {

    @GetMapping("/initClient")
    short initClient();

    @PostMapping("/move")
    short[] move(@RequestBody MoveRequest moveRequest);
}
