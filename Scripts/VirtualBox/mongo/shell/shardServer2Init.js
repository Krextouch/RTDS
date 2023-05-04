/**
 * shardServer2Init.js
 * mongosh script for initalizing the shard2 replica set
 * author:     inf20133@lehre.dhbw-stuttgart.de
 * date:       04.05.2023
 * version:    1.0.0
 */

rs.initiate(
    {
        _id: "RTDSshard2",
        members: [
            { _id: 0, host: "n1:27020" },
            { _id: 1, host: "n2:27020" },
            { _id: 2, host: "n3:27020", priority: 2 },
        ]
    }
)
