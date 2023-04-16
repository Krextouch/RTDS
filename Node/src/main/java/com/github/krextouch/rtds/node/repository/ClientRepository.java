package com.github.krextouch.rtds.node.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface ClientRepository extends MongoRepository<Client, Short> {

    Optional<Client> findClientById(short id);

}
