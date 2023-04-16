package com.github.krextouch.rtds.node.service.node;

import com.github.krextouch.rtds.node.service.model.MoveRequest;
import org.springframework.stereotype.Controller;

@Controller
public class NodeControllerImpl implements NodeController {

    private final NodeService nodeService;

    public NodeControllerImpl(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @Override
    public void hello() {
        nodeService.hello();
    }

    @Override
    public short[] move(MoveRequest moveRequest) {
        short[] nextStep;
        if (moveRequest.getClientId() <= 0) {
            nextStep = nodeService.moveClient(
                    moveRequest.getCurPos(),
                    moveRequest.getDestPos()
            );
        } else {
            nextStep = nodeService.moveClient(
                    moveRequest.getClientId(),
                    moveRequest.getCurPos(),
                    moveRequest.getDestPos()
            );
        }
        return nextStep;
    }
}
