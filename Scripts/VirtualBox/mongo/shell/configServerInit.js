/**
 * configServerInit.js
 * mongosh script for initalizing the config replica set
 * author:     inf20133@lehre.dhbw-stuttgart.de
 * date:       04.05.2023
 * version:    1.0.0
 */

rs.initiate(
    {
        _id: "RTDSconfig",
        configsvr: true,
        members: [
            { _id: 0, host: "n1:27019", priority: 2 },
            { _id: 1, host: "n2:27019" },
            { _id: 2, host: "n3:27019" },
        ]
    }
)
