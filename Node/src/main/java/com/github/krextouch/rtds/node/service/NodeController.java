package com.github.krextouch.rtds.node.service;

import com.github.krextouch.rtds.node.service.model.MoveRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public interface NodeController {

    @GetMapping("/hello")
    void hello();

    @PostMapping("/move")
    String move(@RequestBody MoveRequest moveRequest);

}
