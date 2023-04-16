package com.github.krextouch.rtds.node.client;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Setter
@Document("client")
public class Client {

    @Id
    public String id;

    public String name;
}
