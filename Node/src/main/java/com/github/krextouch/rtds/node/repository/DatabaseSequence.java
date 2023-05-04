package com.github.krextouch.rtds.node.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * DatabaseSequence
 * class used for generating ids
 * author:     inf20020@lehre.dhbw-stuttgart.de
 * date:       04.05.2023
 * version:    1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@Document(collection = "database_sequences")
public class DatabaseSequence {

    @Id
    private String id;

    private short seq;
}