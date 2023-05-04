/**
 * shardServer1Init.js
 * mongosh script for initalizing the shard1 replica set
 * author:     inf20133@lehre.dhbw-stuttgart.de
 * date:       04.05.2023
 * version:    1.0.0
 */

rs.initiate(
    {
        _id: "RTDSshard1",
        members: [
            { _id: 0, host: "n1:27018" },
            { _id: 1, host: "n2:27018", priority: 2 },
            { _id: 2, host: "n3:27018" },
        ]
    }
)
