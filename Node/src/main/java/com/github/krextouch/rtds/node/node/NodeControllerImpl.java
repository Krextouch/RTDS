package com.github.krextouch.rtds.node.node;

import com.github.krextouch.rtds.node.model.MoveRequest;
import org.springframework.stereotype.Controller;

/**
 * NodeControllerImpl
 * controller for handling requests and forwarding them to the correct function
 * author:     inf20020@lehre.dhbw-stuttgart.de
 * date:       04.05.2023
 * version:    1.0.0
 */
@Controller
public class NodeControllerImpl implements NodeController {

    private final NodeService nodeService;

    public NodeControllerImpl(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @Override
    public void health() {
    }

    @Override
    public short initClient() {
        return nodeService.initClient();
    }

    @Override
    public short[] move(MoveRequest moveRequest) {
        return nodeService.moveClient(
                moveRequest.getClientId(),
                moveRequest.getCurPos(),
                moveRequest.getDestPos()
        );
    }
}
