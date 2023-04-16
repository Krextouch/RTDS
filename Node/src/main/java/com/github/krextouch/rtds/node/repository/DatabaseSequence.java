package com.github.krextouch.rtds.node.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "database_sequences")
public class DatabaseSequence {

    @Id
    private String id;

    private short seq;
}