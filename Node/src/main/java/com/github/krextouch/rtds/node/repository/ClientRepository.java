package com.github.krextouch.rtds.node.repository;

import com.github.krextouch.rtds.node.trafficcontrol.Coordinate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * client repository interface used to create queries for the client
 */
public interface ClientRepository extends MongoRepository<Client, Short> {

    Boolean existsById(short id);
    List<Client> findClientByCurPos(Coordinate curPos);
}
