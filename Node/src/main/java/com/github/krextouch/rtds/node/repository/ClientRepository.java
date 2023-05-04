package com.github.krextouch.rtds.node.repository;

import com.github.krextouch.rtds.node.trafficcontrol.Coordinate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * ClientRepository
 * client repository interface used to create queries for the client
 * author:     inf20020@lehre.dhbw-stuttgart.de
 * date:       04.05.2023
 * version:    1.0.0
 */
public interface ClientRepository extends MongoRepository<Client, Short> {

    Boolean existsById(short id);

    List<Client> findClientByCurPos(Coordinate curPos);
}
