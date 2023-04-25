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
