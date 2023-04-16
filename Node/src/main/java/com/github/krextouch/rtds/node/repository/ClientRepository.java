package com.github.krextouch.rtds.node.repository;

import com.github.krextouch.rtds.node.service.trafficcontrol.Coordinate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface ClientRepository extends MongoRepository<Client, Short> {

    List<Client> findClientByCurPos(Coordinate curPos);
}
