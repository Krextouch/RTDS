package com.github.krextouch.rtds.node.node;

import com.github.krextouch.rtds.node.model.MoveRequest;
import org.springframework.stereotype.Controller;

@Controller
public class NodeControllerImpl implements NodeController {

    private final NodeService nodeService;

    public NodeControllerImpl(NodeService nodeService) {
        this.nodeService = nodeService;
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
